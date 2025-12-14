package com.jojolaptech.camel.route;

import com.jojolaptech.camel.processor.CustomerProcessor;
import lombok.RequiredArgsConstructor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ImportRouteBuilder extends RouteBuilder {

    private final CustomerProcessor customerProcessor;

    @Override
    public void configure() {
        errorHandler(defaultErrorHandler()
                .maximumRedeliveries(3)
                .redeliveryDelay(2000));

        from("jpa:com.jojolaptech.camel.model.mysql.SourceCustomer"
                + "?persistenceUnit=mysqlPU"
                + "&consumer.namedQuery=SourceCustomer.fetchUnexported"
                + "&consumeDelete=false"
                + "&maximumResults=50"
                + "&delay=5000")
                .routeId("mysql-to-postgres-import")
                .log("Consuming customer ${body.id}")
                .process(customerProcessor);
    }
}

