package com.jojolaptech.camel.processor;

import com.jojolaptech.camel.model.mysql.tendersystem.UserNotes;
import com.jojolaptech.camel.model.postgres.iam.UserEntity;
import com.jojolaptech.camel.model.postgres.notice.NoticeBookmarkEntity;
import com.jojolaptech.camel.model.postgres.notice.TenderNoticeEntity;
import com.jojolaptech.camel.repository.postgres.iam.UserRepository;
import com.jojolaptech.camel.repository.postgres.notice.NoticeBookmarkRepository;
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
public class NoticeBookmarkProcessor implements Processor {

    private static final Logger log = LoggerFactory.getLogger(NoticeBookmarkProcessor.class);

    private final NoticeBookmarkRepository noticeBookmarkRepository;
    private final UserRepository userRepository;
    private final TenderNoticeRepository tenderNoticeRepository;

    @Override
    public void process(Exchange exchange) throws InvalidPayloadException {
        UserNotes source = exchange.getMessage().getMandatoryBody(UserNotes.class);

        log.info("Migrating user_notes id={} to notice_bookmark", source.getId());

        // Check if already exists
        if (noticeBookmarkRepository.existsByMysqlId(source.getId())) {
            log.info("Skipping user_notes id={}, already exists", source.getId());
            return;
        }

        // Look up user by mysqlId -> userId
        UUID userId = null;
        if (source.getSecUser() != null && source.getSecUser().getId() != null) {
            Optional<UserEntity> userOpt = userRepository.findByMysqlId(source.getSecUser().getId());
            if (userOpt.isPresent()) {
                userId = userOpt.get().getId();
                log.debug("Found user mysqlId={}, postgresId={}", source.getSecUser().getId(), userId);
            } else {
                log.warn("User not found in Postgres for mysqlId={}, skipping bookmark", source.getSecUser().getId());
                return;
            }
        } else {
            log.warn("UserNotes id={} has no user relationship, skipping bookmark", source.getId());
            return;
        }

        // Look up notice by mysqlId -> notice entity
        TenderNoticeEntity notice = null;
        if (source.getNotice() != null && source.getNotice().getId() != null) {
            Optional<TenderNoticeEntity> noticeOpt = tenderNoticeRepository.findByMysqlId(source.getNotice().getId());
            if (noticeOpt.isPresent()) {
                notice = noticeOpt.get();
                log.debug("Found notice mysqlId={}, postgresId={}", source.getNotice().getId(), notice.getId());
            } else {
                log.warn("Notice not found in Postgres for mysqlId={}, skipping bookmark", source.getNotice().getId());
                return;
            }
        } else {
            log.warn("UserNotes id={} has no notice relationship, skipping bookmark", source.getId());
            return;
        }

        NoticeBookmarkEntity target = new NoticeBookmarkEntity();
        target.setMysqlId(source.getId());
        target.setVersion(0L);
        target.setUserId(userId);
        target.setNotice(notice);

        NoticeBookmarkEntity saved = noticeBookmarkRepository.save(target);
        log.info("Saved notice_bookmark to Postgres with id={}, mysqlId={}, userId={}, noticeId={}", 
                saved.getId(), saved.getMysqlId(), saved.getUserId(), saved.getNotice().getId());
    }
}

