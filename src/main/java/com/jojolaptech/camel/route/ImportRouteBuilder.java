package com.jojolaptech.camel.route;

import com.jojolaptech.camel.processor.CustomerProcessor;
import com.jojolaptech.camel.repository.mysql.SourceCustomerRepository;
import com.jojolaptech.camel.repository.mysql.sec.SecUserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ImportRouteBuilder extends RouteBuilder {

    private static final Logger log = LoggerFactory.getLogger(ImportRouteBuilder.class);

    private final CustomerProcessor customerProcessor;
//    private final SourceCustomerRepository sourceCustomerRepository;
    private final SecUserRepository secUserRepository;

    @Override
    public void configure() {
        errorHandler(defaultErrorHandler()
                .maximumRedeliveries(3)
                .redeliveryDelay(2000));

        from("timer:mysql-import?repeatCount=1&delay=0")
                .routeId("mysql-to-postgres-import")
                .setBody(exchange ->
                        secUserRepository.findAll())
                .log("Loaded ${body.size()} sec_user rows from MySQL")
                .split(body())
                .log("Consuming customer ${body.id}")
                .process(customerProcessor);
    }
}

