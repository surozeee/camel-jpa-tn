package com.jojolaptech.camel.processor;

import com.jojolaptech.camel.model.mysql.tendersystem.Tag;
import com.jojolaptech.camel.model.postgres.notice.TagsEntity;
import com.jojolaptech.camel.model.postgres.notice.TenderNoticeEntity;
import com.jojolaptech.camel.repository.postgres.notice.TagsRepository;
import com.jojolaptech.camel.repository.postgres.notice.TenderNoticeRepository;
import lombok.RequiredArgsConstructor;
import org.apache.camel.Exchange;
import org.apache.camel.InvalidPayloadException;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class TagProcessor implements Processor {

    private static final Logger log = LoggerFactory.getLogger(TagProcessor.class);

    private final TagsRepository tagsRepository;
    private final TenderNoticeRepository tenderNoticeRepository;

    @Override
    public void process(Exchange exchange) throws InvalidPayloadException {
        Tag source = exchange.getMessage().getMandatoryBody(Tag.class);

        log.info("Migrating tag id={}, name={}", source.getId(), source.getName());

        // Check if already exists
        if (tagsRepository.existsByMysqlId(source.getId())) {
            log.info("Skipping tag id={}, already exists", source.getId());
            return;
        }

        // Source tag table has no user reference in this environment.
        UUID userId = null;

        // Look up notice by mysqlId
        UUID noticeId = null;
        if (source.getNotice() != null && source.getNotice().getId() != null) {
            Optional<TenderNoticeEntity> noticeOpt = tenderNoticeRepository.findByMysqlId(source.getNotice().getId());
            if (noticeOpt.isPresent()) {
                noticeId = noticeOpt.get().getId();
                log.debug("Found notice with mysqlId={}, postgresId={}", source.getNotice().getId(), noticeId);
            } else {
                log.warn("Notice not found in Postgres for mysqlId={}", source.getNotice().getId());
            }
        }

        TagsEntity target = new TagsEntity();
        target.setMysqlId(source.getId());
        target.setVersion(0L);
        target.setName(source.getName());
        target.setUserId(userId);
        target.setNoticeId(noticeId);

        TagsEntity saved = tagsRepository.save(target);
        log.info("Saved tags to Postgres with id={}, mysqlId={}, name={}", saved.getId(), saved.getMysqlId(), saved.getName());
    }
}

