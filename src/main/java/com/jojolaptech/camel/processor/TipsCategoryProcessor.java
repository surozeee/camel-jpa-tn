package com.jojolaptech.camel.processor;

import com.jojolaptech.camel.model.mysql.tendersystem.TipsCategory;
import com.jojolaptech.camel.model.postgres.notice.TipsCategoryEntity;
import com.jojolaptech.camel.repository.postgres.notice.TipsCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.apache.camel.Exchange;
import org.apache.camel.InvalidPayloadException;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
@RequiredArgsConstructor
public class TipsCategoryProcessor implements Processor {

    private static final Logger log = LoggerFactory.getLogger(TipsCategoryProcessor.class);

    private final TipsCategoryRepository tipsCategoryRepository;

    @Override
    public void process(Exchange exchange) throws InvalidPayloadException {
        TipsCategory source = exchange.getMessage().getMandatoryBody(TipsCategory.class);

        log.info("Migrating tips_category id={}, name={}", source.getId(), source.getTipCategory());

        // Check if already exists
        if (tipsCategoryRepository.existsByMysqlId(source.getId())) {
            log.info("Skipping tips_category id={}, already exists", source.getId());
            return;
        }

        TipsCategoryEntity target = new TipsCategoryEntity();
        target.setMysqlId(source.getId());
        target.setName(source.getTipCategory());
        // Status will be set to ACTIVE by @PrePersist (if it exists, otherwise it will be null which is fine)

        TipsCategoryEntity saved = tipsCategoryRepository.save(target);
        log.info("Saved tips_category to Postgres with id={}, name={}", saved.getId(), saved.getName());
    }
}

