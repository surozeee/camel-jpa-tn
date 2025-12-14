package com.jojolaptech.camel.processor;

import com.jojolaptech.camel.model.mysql.SourceCustomer;
import com.jojolaptech.camel.model.postgres.TargetCustomer;
import com.jojolaptech.camel.repository.mysql.SourceCustomerRepository;
import com.jojolaptech.camel.repository.postgres.TargetCustomerRepository;
import lombok.RequiredArgsConstructor;
import org.apache.camel.Exchange;
import org.apache.camel.InvalidPayloadException;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerProcessor implements Processor {

    private final TargetCustomerRepository targetCustomerRepository;
    private final SourceCustomerRepository sourceCustomerRepository;

    @Override
    public void process(Exchange exchange) throws InvalidPayloadException {
        SourceCustomer source = exchange.getMessage().getMandatoryBody(SourceCustomer.class);

        TargetCustomer target = new TargetCustomer();
        target.setSourceId(source.getId());
        target.setName(source.getName());
        target.setEmail(source.getEmail());
        target.setCreatedAt(source.getCreatedAt());

        targetCustomerRepository.save(target);

        source.setExported(true);
        sourceCustomerRepository.save(source);
    }
}

