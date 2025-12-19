package com.jojolaptech.camel.processor;

import com.jojolaptech.camel.model.mysql.tendersystem.ProductService;
import com.jojolaptech.camel.model.postgres.notice.ProductServiceEntity;
import com.jojolaptech.camel.repository.postgres.notice.ProductServiceRepository;
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

    private final ProductServiceRepository productServiceRepository;

    @Override
    public void process(Exchange exchange) throws InvalidPayloadException {
        ProductService source = exchange.getMessage().getMandatoryBody(ProductService.class);

        log.info("Migrating product_service id={}, name={}", source.getId(), source.getName());

        // Check if already exists by mysqlId
        if (productServiceRepository.existsByMysqlId(source.getId())) {
            log.info("Skipping product_service id={}, already exists by mysqlId", source.getId());
            return;
        }

        ProductServiceEntity target = new ProductServiceEntity();
        target.setMysqlId(source.getId());
        target.setName(source.getName());
        target.setVersion(0L);
        // Status will be set to ACTIVE by @PrePersist

        ProductServiceEntity saved = productServiceRepository.save(target);
        log.info("Saved product_service to Postgres with id={}, mysqlId={}, name={}", saved.getId(), saved.getMysqlId(), saved.getName());
    }
}

