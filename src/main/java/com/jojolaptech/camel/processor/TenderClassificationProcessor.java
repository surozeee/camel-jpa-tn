package com.jojolaptech.camel.processor;

import com.jojolaptech.camel.model.mysql.tendersystem.TenderClassification;
import com.jojolaptech.camel.model.postgres.notice.DistrictEntity;
import com.jojolaptech.camel.repository.postgres.notice.DistrictRepository;
import lombok.RequiredArgsConstructor;
import org.apache.camel.Exchange;
import org.apache.camel.InvalidPayloadException;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
@RequiredArgsConstructor
public class TenderClassificationProcessor implements Processor {

    private static final Logger log = LoggerFactory.getLogger(TenderClassificationProcessor.class);

    private final DistrictRepository districtRepository;

    @Override
    public void process(Exchange exchange) throws InvalidPayloadException {
        TenderClassification source = exchange.getMessage().getMandatoryBody(TenderClassification.class);

        log.info("Migrating tender_classification id={}, name={} to district", source.getId(), source.getClassificationName());

        DistrictEntity target = new DistrictEntity();
        target.setMysqlId(source.getId());
        target.setName(source.getClassificationName());
        
        // Generate code from classification name (first 3 uppercase letters or use ID)
        String code = source.getClassificationName() != null && source.getClassificationName().length() >= 3
                ? source.getClassificationName().substring(0, Math.min(3, source.getClassificationName().length())).toUpperCase()
                : "TC" + source.getId();
        target.setCode(code);
        
        // Use classification name as headquarter (required field)
        target.setHeadquarter(source.getClassificationName() != null ? source.getClassificationName() : "Unknown");
        
        // Status will be set to ACTIVE by @PrePersist

        DistrictEntity saved = districtRepository.save(target);
        log.info("Saved district to Postgres with id={}, name={}, code={}", saved.getId(), saved.getName(), saved.getCode());
    }
}

