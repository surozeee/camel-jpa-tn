package com.jojolaptech.camel.processor;

import com.jojolaptech.camel.model.mysql.tendersystem.Category;
import com.jojolaptech.camel.model.postgres.notice.NoticeCategoryEntity;
import com.jojolaptech.camel.repository.postgres.notice.NoticeCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.apache.camel.Exchange;
import org.apache.camel.InvalidPayloadException;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
@RequiredArgsConstructor
public class CategoryProcessor implements Processor {

    private static final Logger log = LoggerFactory.getLogger(CategoryProcessor.class);

    private final NoticeCategoryRepository noticeCategoryRepository;

    @Override
    public void process(Exchange exchange) throws InvalidPayloadException {
        Category source = exchange.getMessage().getMandatoryBody(Category.class);

        log.info("Migrating category id={}, name={}", source.getId(), source.getCategoryName());

        NoticeCategoryEntity target = new NoticeCategoryEntity();
        target.setMysqlId(source.getId());
        target.setName(source.getCategoryName());
        // Status will be set to ACTIVE by @PrePersist

        NoticeCategoryEntity saved = noticeCategoryRepository.save(target);
        log.info("Saved notice_category to Postgres with id={}, name={}", saved.getId(), saved.getName());
    }
}

