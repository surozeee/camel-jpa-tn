package com.jojolaptech.camel.processor;

import com.jojolaptech.camel.model.mysql.tendersystem.ProductService;
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
public class ProductServiceProcessor implements Processor {

    private static final Logger log = LoggerFactory.getLogger(ProductServiceProcessor.class);

    private final NoticeCategoryRepository noticeCategoryRepository;

    @Override
    public void process(Exchange exchange) throws InvalidPayloadException {
        ProductService source = exchange.getMessage().getMandatoryBody(ProductService.class);

        log.info("Migrating product_service id={}, name={} to notice_category", source.getId(), source.getName());

        NoticeCategoryEntity target = new NoticeCategoryEntity();
        target.setMysqlId(source.getId());
        target.setName(source.getName());
        // Status will be set to ACTIVE by @PrePersist

        NoticeCategoryEntity saved = noticeCategoryRepository.save(target);
        log.info("Saved notice_category to Postgres with id={}, name={}", saved.getId(), saved.getName());
    }
}

