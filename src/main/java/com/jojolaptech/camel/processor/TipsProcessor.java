package com.jojolaptech.camel.processor;

import com.jojolaptech.camel.model.mysql.tendersystem.Tips;
import com.jojolaptech.camel.model.postgres.enums.StatusEnum;
import com.jojolaptech.camel.model.postgres.notice.TipsCategoryEntity;
import com.jojolaptech.camel.model.postgres.notice.TipsEntity;
import com.jojolaptech.camel.repository.postgres.notice.TipsCategoryRepository;
import com.jojolaptech.camel.repository.postgres.notice.TipsRepository;
import lombok.RequiredArgsConstructor;
import org.apache.camel.Exchange;
import org.apache.camel.InvalidPayloadException;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TipsProcessor implements Processor {

    private static final Logger log = LoggerFactory.getLogger(TipsProcessor.class);

    private final TipsRepository tipsRepository;
    private final TipsCategoryRepository tipsCategoryRepository;

    @Override
    public void process(Exchange exchange) throws InvalidPayloadException {
        Tips source = exchange.getMessage().getMandatoryBody(Tips.class);

        log.info("Migrating tips id={}, tipNote length={}", source.getId(), 
                source.getTipNote() != null ? source.getTipNote().length() : 0);

        // Look up tips category by mysqlId
        TipsCategoryEntity category = null;
        if (source.getTipsCategory() != null && source.getTipsCategory().getId() != null) {
            Optional<TipsCategoryEntity> categoryOpt = tipsCategoryRepository.findByMysqlId(source.getTipsCategory().getId());
            if (categoryOpt.isPresent()) {
                category = categoryOpt.get();
                log.debug("Found tipsCategory with mysqlId={}, postgresId={}", source.getTipsCategory().getId(), category.getId());
            } else {
                log.warn("TipsCategory not found in Postgres for mysqlId={}", source.getTipsCategory().getId());
            }
        }

        TipsEntity target = new TipsEntity();
        target.setMysqlId(source.getId());
        target.setContent(source.getTipNote());
        // Title is not in MySQL, leave it null
        
        // Map Boolean isActive to StatusEnum
        if (source.getIsActive() != null && source.getIsActive()) {
            target.setStatus(StatusEnum.ACTIVE);
        } else {
            target.setStatus(StatusEnum.INACTIVE);
        }
        
        target.setCategory(category);

        TipsEntity saved = tipsRepository.save(target);
        log.info("Saved tips to Postgres with id={}, mysqlId={}", saved.getId(), saved.getMysqlId());
    }
}

