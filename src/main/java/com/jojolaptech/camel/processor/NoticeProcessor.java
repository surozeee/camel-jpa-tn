package com.jojolaptech.camel.processor;

import com.jojolaptech.camel.model.mysql.enums.Status;
import com.jojolaptech.camel.model.mysql.tendersystem.Notice;
import com.jojolaptech.camel.model.postgres.enums.TenderNoticeStatus;
import com.jojolaptech.camel.model.postgres.notice.*;
import com.jojolaptech.camel.repository.postgres.notice.*;
import lombok.RequiredArgsConstructor;
import org.apache.camel.Exchange;
import org.apache.camel.InvalidPayloadException;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class NoticeProcessor implements Processor {

    private static final Logger log = LoggerFactory.getLogger(NoticeProcessor.class);

    private final TenderNoticeRepository tenderNoticeRepository;
    private final NoticeCategoryRepository noticeCategoryRepository;
    private final IndustryRepository industryRepository;
    private final ProductServiceRepository productServiceRepository;
    private final DistrictRepository districtRepository;
    private final NewsPaperRepository newsPaperRepository;

    @Override
    public void process(Exchange exchange) throws InvalidPayloadException {
        Notice source = exchange.getMessage().getMandatoryBody(Notice.class);

        log.info("Migrating notice id={}, provider={}", source.getId(), source.getNoticeProvider());

        // Check if already exists
        if (tenderNoticeRepository.existsByMysqlId(source.getId())) {
            log.info("Skipping notice id={}, already exists", source.getId());
            return;
        }

        // Look up related entities by mysqlId and get their UUIDs
        
        // sourceId -> NewsPaperEntity with mysqlId
        UUID sourceId = null;
        if (source.getNewsPaper() != null && source.getNewsPaper().getId() != null) {
            Optional<NewsPaperEntity> newsPaperOpt = newsPaperRepository.findByMysqlId(source.getNewsPaper().getId());
            if (newsPaperOpt.isPresent()) {
                sourceId = newsPaperOpt.get().getId();
                log.debug("Found NewsPaper mysqlId={}, postgresId={}", source.getNewsPaper().getId(), sourceId);
            } else {
                log.error("NewsPaper not found in Postgres for mysqlId={}. Notice id={} cannot be migrated without sourceId.", 
                        source.getNewsPaper().getId(), source.getId());
                return;
            }
        } else {
            log.error("No newsPaper for notice id={}. Notice cannot be migrated without sourceId.", source.getId());
            return;
        }

        // districtId -> DistrictEntity with mysqlId
        UUID districtId = null;
        if (source.getTenderClassification() != null && source.getTenderClassification().getId() != null) {
            Optional<DistrictEntity> districtOpt = districtRepository.findByMysqlId(source.getTenderClassification().getId());
            if (districtOpt.isPresent()) {
                districtId = districtOpt.get().getId();
                log.debug("Found District mysqlId={}, postgresId={}", source.getTenderClassification().getId(), districtId);
            } else {
                log.warn("District (tenderClassification) not found in Postgres for mysqlId={}", source.getTenderClassification().getId());
            }
        } else {
            log.warn("Notice id={} has no TenderClassification relationship", source.getId());
        }

        // categoryId -> NoticeCategoryEntity with mysqlId
        UUID categoryId = null;
        if (source.getCategory() != null && source.getCategory().getId() != null) {
            Optional<NoticeCategoryEntity> categoryOpt = noticeCategoryRepository.findByMysqlId(source.getCategory().getId());
            if (categoryOpt.isPresent()) {
                categoryId = categoryOpt.get().getId();
                log.debug("Found NoticeCategory mysqlId={}, postgresId={}", source.getCategory().getId(), categoryId);
            } else {
                log.warn("Category not found in Postgres for mysqlId={}", source.getCategory().getId());
            }
        }

        // industryId -> IndustryEntity with mysqlId
        UUID industryId = null;
        if (source.getIndustry() != null && source.getIndustry().getId() != null) {
            Optional<IndustryEntity> industryOpt = industryRepository.findByMysqlId(source.getIndustry().getId());
            if (industryOpt.isPresent()) {
                industryId = industryOpt.get().getId();
                log.debug("Found Industry mysqlId={}, postgresId={}", source.getIndustry().getId(), industryId);
            } else {
                log.warn("Industry not found in Postgres for mysqlId={}", source.getIndustry().getId());
            }
        } else {
            log.warn("Notice id={} has no Industry relationship", source.getId());
        }

        // productServiceId -> ProductServiceEntity with mysqlId
        UUID productServiceId = null;
        if (source.getProductService() != null && source.getProductService().getId() != null) {
            Optional<ProductServiceEntity> productServiceOpt = productServiceRepository.findByMysqlId(source.getProductService().getId());
            if (productServiceOpt.isPresent()) {
                productServiceId = productServiceOpt.get().getId();
                log.debug("Found ProductService mysqlId={}, postgresId={}", source.getProductService().getId(), productServiceId);
            } else {
                log.warn("ProductService not found in Postgres for mysqlId={}", source.getProductService().getId());
            }
        } else {
            log.warn("Notice id={} has no ProductService relationship", source.getId());
        }

        // Validate required fields - skip if any required field is missing
        if (categoryId == null || sourceId == null) {
            log.error("Missing required relationships for notice id={}. categoryId={}, sourceId={}. Skipping migration.", 
                    source.getId(), categoryId, sourceId);
            return;
        }
        
        // Log warnings for optional fields that are missing
        if (districtId == null) {
            log.warn("Notice id={} has no District (TenderClassification) relationship - will be saved with null districtId", source.getId());
        }
        if (industryId == null) {
            log.warn("Notice id={} has no Industry relationship - will be saved with null industryId", source.getId());
        }
        if (productServiceId == null) {
            log.warn("Notice id={} has no ProductService relationship - will be saved with null productServiceId", source.getId());
        }

        TenderNoticeEntity target = new TenderNoticeEntity();
        target.setMysqlId(source.getId());
        target.setVersion(0L);
        
        // Set foreign key UUIDs from mysqlId lookups
        target.setSourceId(sourceId);           // NewsPaperEntity UUID from mysqlId lookup
        target.setDistrictId(districtId);       // DistrictEntity UUID from mysqlId lookup
        target.setCategoryId(categoryId);       // NoticeCategoryEntity UUID from mysqlId lookup
        target.setIndustryId(industryId);       // IndustryEntity UUID from mysqlId lookup
        target.setProductServiceId(productServiceId); // ProductServiceEntity UUID from mysqlId lookup
        
        target.setNoticeProvider(source.getNoticeProvider());
        target.setDescription(source.getDescription());
        
        // Convert Instant to LocalDateTime
        if (source.getPublishDate() != null) {
            LocalDateTime publishDate = source.getPublishDate()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime();
            target.setPublishDate(publishDate);
        }
        
        if (source.getLastDate() != null) {
            LocalDateTime submitDate = source.getLastDate()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime();
            target.setSubmitDate(submitDate);
        }
        
        target.setRemarks(source.getRemark());
        target.setNoticeImage(source.getImgName());
        target.setDocument(source.getDocument());
        
        if (source.getMailTime() != null) {
            LocalDateTime emailTime = source.getMailTime()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime();
            target.setEmailTime(emailTime);
        }
        
        // Map parentNotice -> TenderNoticeEntity with mysqlId
        if (source.getLinkedNotice() != null && source.getLinkedNotice().getId() != null) {
            Optional<TenderNoticeEntity> parentNoticeOpt = tenderNoticeRepository.findByMysqlId(source.getLinkedNotice().getId());
            if (parentNoticeOpt.isPresent()) {
                TenderNoticeEntity parentNotice = parentNoticeOpt.get();
                target.setParentNotice(parentNotice);
                target.setLinkedNotice(true);
                log.debug("Found parent notice mysqlId={}, postgresId={}", source.getLinkedNotice().getId(), parentNotice.getId());
            } else {
                log.warn("Parent notice not found in Postgres for mysqlId={}, will set linkedNotice flag but no parent", source.getLinkedNotice().getId());
                target.setLinkedNotice(true);
            }
        } else {
            target.setLinkedNotice(false);
        }
        
        // Map Status enum
        if (source.getStatus() != null) {
            switch (source.getStatus()) {
                case New:
                    target.setStatus(TenderNoticeStatus.UNPUBLISHED);
                    break;
                case Published:
                    target.setStatus(TenderNoticeStatus.PUBLISHED);
                    break;
                case Delete:
                    target.setStatus(TenderNoticeStatus.DELETED);
                    break;
                default:
                    target.setStatus(TenderNoticeStatus.UNPUBLISHED);
            }
        } else {
            target.setStatus(TenderNoticeStatus.UNPUBLISHED);
        }

        TenderNoticeEntity saved = tenderNoticeRepository.save(target);
        log.info("Saved tender_notice to Postgres with id={}, mysqlId={}", saved.getId(), saved.getMysqlId());
    }
}

