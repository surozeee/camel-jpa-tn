package com.jojolaptech.camel.processor;

import com.jojolaptech.camel.model.mysql.tendersystem.NewsPaper;
import com.jojolaptech.camel.model.postgres.notice.NewsPaperEntity;
import com.jojolaptech.camel.repository.postgres.notice.NewsPaperRepository;
import lombok.RequiredArgsConstructor;
import org.apache.camel.Exchange;
import org.apache.camel.InvalidPayloadException;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
@RequiredArgsConstructor
public class NewsPaperProcessor implements Processor {

    private static final Logger log = LoggerFactory.getLogger(NewsPaperProcessor.class);

    private final NewsPaperRepository newsPaperRepository;

    @Override
    public void process(Exchange exchange) throws InvalidPayloadException {
        NewsPaper source = exchange.getMessage().getMandatoryBody(NewsPaper.class);

        log.info("Migrating news_paper id={}, name={}", source.getId(), source.getName());

        // Check if already exists
        if (newsPaperRepository.existsByMysqlId(source.getId())) {
            log.info("Skipping news_paper id={}, already exists", source.getId());
            return;
        }

        NewsPaperEntity target = new NewsPaperEntity();
        target.setMysqlId(source.getId());
        target.setName(source.getName());
        // Status will be set to ACTIVE by @PrePersist

        NewsPaperEntity saved = newsPaperRepository.save(target);
        log.info("Saved news_paper to Postgres with id={}, name={}", saved.getId(), saved.getName());
    }
}

