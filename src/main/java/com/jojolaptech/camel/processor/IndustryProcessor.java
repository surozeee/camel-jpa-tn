package com.jojolaptech.camel.processor;

import com.jojolaptech.camel.model.mysql.tendersystem.Industry;
import com.jojolaptech.camel.model.postgres.notice.IndustryEntity;
import com.jojolaptech.camel.repository.postgres.notice.IndustryRepository;
import lombok.RequiredArgsConstructor;
import org.apache.camel.Exchange;
import org.apache.camel.InvalidPayloadException;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
@RequiredArgsConstructor
public class IndustryProcessor implements Processor {

    private static final Logger log = LoggerFactory.getLogger(IndustryProcessor.class);

    private final IndustryRepository industryRepository;

    @Override
    public void process(Exchange exchange) throws InvalidPayloadException {
        Industry source = exchange.getMessage().getMandatoryBody(Industry.class);

        log.info("Migrating industry id={}, name={}", source.getId(), source.getName());

        // Check if already exists
        if (industryRepository.existsByMysqlId(source.getId())) {
            log.info("Skipping industry id={}, already exists", source.getId());
            return;
        }

        IndustryEntity target = new IndustryEntity();
        target.setMysqlId(source.getId());
        target.setName(source.getName());
        // Status will be set to ACTIVE by @PrePersist

        IndustryEntity saved = industryRepository.save(target);
        log.info("Saved industry to Postgres with id={}, name={}", saved.getId(), saved.getName());
    }
}

