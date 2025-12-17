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

        // Look up related entities by mysqlId
        UUID categoryId = null;
        if (source.getCategory() != null && source.getCategory().getId() != null) {
            Optional<NoticeCategoryEntity> categoryOpt = noticeCategoryRepository.findByMysqlId(source.getCategory().getId());
            if (categoryOpt.isPresent()) {
                categoryId = categoryOpt.get().getId();
            } else {
                log.warn("Category not found in Postgres for mysqlId={}", source.getCategory().getId());
            }
        }

        UUID industryId = null;
        if (source.getIndustry() != null && source.getIndustry().getId() != null) {
            Optional<IndustryEntity> industryOpt = industryRepository.findByMysqlId(source.getIndustry().getId());
            if (industryOpt.isPresent()) {
                industryId = industryOpt.get().getId();
            } else {
                log.warn("Industry not found in Postgres for mysqlId={}", source.getIndustry().getId());
            }
        }

        UUID productServiceId = null;
        if (source.getProductService() != null && source.getProductService().getId() != null) {
            Optional<ProductServiceEntity> productServiceOpt = productServiceRepository.findByMysqlId(source.getProductService().getId());
            if (productServiceOpt.isPresent()) {
                productServiceId = productServiceOpt.get().getId();
            } else {
                log.warn("ProductService not found in Postgres for mysqlId={}", source.getProductService().getId());
            }
        }

        UUID districtId = null;
        if (source.getTenderClassification() != null && source.getTenderClassification().getId() != null) {
            Optional<DistrictEntity> districtOpt = districtRepository.findByMysqlId(source.getTenderClassification().getId());
            if (districtOpt.isPresent()) {
                districtId = districtOpt.get().getId();
            } else {
                log.warn("District (tenderClassification) not found in Postgres for mysqlId={}", source.getTenderClassification().getId());
            }
        }

        UUID sourceId = null;
        if (source.getNewsPaper() != null && source.getNewsPaper().getId() != null) {
            Optional<NewsPaperEntity> newsPaperOpt = newsPaperRepository.findByMysqlId(source.getNewsPaper().getId());
            if (newsPaperOpt.isPresent()) {
                sourceId = newsPaperOpt.get().getId();
            } else {
                log.warn("NewsPaper not found in Postgres for mysqlId={}, using notice ID as sourceId", source.getNewsPaper().getId());
                // Use a default UUID if newsPaper not found - this might need adjustment
                sourceId = UUID.randomUUID();
            }
        } else {
            // Generate a default sourceId if newsPaper is null
            sourceId = UUID.randomUUID();
            log.warn("No newsPaper for notice id={}, using generated UUID as sourceId", source.getId());
        }

        // Validate required fields
        if (categoryId == null || industryId == null || productServiceId == null || sourceId == null) {
            log.error("Missing required relationships for notice id={}. Skipping migration.", source.getId());
            return;
        }

        TenderNoticeEntity target = new TenderNoticeEntity();
        target.setMysqlId(source.getId());
        target.setSourceId(sourceId);
        target.setDistrictId(districtId);
        target.setCategoryId(categoryId);
        target.setIndustryId(industryId);
        target.setProductServiceId(productServiceId);
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
        
        // Map linked notice
        if (source.getLinkedNotice() != null && source.getLinkedNotice().getId() != null) {
            Optional<TenderNoticeEntity> parentNoticeOpt = tenderNoticeRepository.findByMysqlId(source.getLinkedNotice().getId());
            if (parentNoticeOpt.isPresent()) {
                target.setParentNotice(parentNoticeOpt.get());
                target.setLinkedNotice(true);
            } else {
                log.warn("Linked notice not found in Postgres for mysqlId={}, will set linkedNotice flag but no parent", source.getLinkedNotice().getId());
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

