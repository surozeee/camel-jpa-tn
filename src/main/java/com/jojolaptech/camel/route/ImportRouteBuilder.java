package com.jojolaptech.camel.route;

import com.jojolaptech.camel.processor.CustomerProcessor;
import com.jojolaptech.camel.repository.mysql.SourceCustomerRepository;
import lombok.RequiredArgsConstructor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ImportRouteBuilder extends RouteBuilder {

    private final CustomerProcessor customerProcessor;
    private final SourceCustomerRepository sourceCustomerRepository;

    @Override
    public void configure() {
        errorHandler(defaultErrorHandler()
                .maximumRedeliveries(3)
                .redeliveryDelay(2000));

        from("timer:mysql-import?fixedRate=true&period=5000")
                .routeId("mysql-to-postgres-import")
                .setBody(exchange ->
                        sourceCustomerRepository.findTop50ByExportedFalseOrExportedIsNullOrderByIdAsc())
                .split(body())
                .log("Consuming customer ${body.id}")
                .process(customerProcessor);
    }
}

