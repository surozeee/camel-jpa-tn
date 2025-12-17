package com.jojolaptech.camel.route;

import com.jojolaptech.camel.processor.CustomerProcessor;
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
    private final SecUserRepository secUserRepository;

    private static final int PAGE_SIZE = 500; // tune this

    @Override
    public void configure() {

        errorHandler(defaultErrorHandler()
                .maximumRedeliveries(3)
                .redeliveryDelay(2000));

        from("timer:mysql-import?repeatCount=1&delay=0")
                .routeId("mysql-to-postgres-import")
                .setProperty("page").constant(0)
                .setProperty("hasNext").constant(true)

                .loopDoWhile(exchange -> Boolean.TRUE.equals(exchange.getProperty("hasNext", Boolean.class)))
                .process(exchange -> {
                    int page = exchange.getProperty("page", Integer.class);
                    var pageable = org.springframework.data.domain.PageRequest.of(page, PAGE_SIZE,
                            org.springframework.data.domain.Sort.by("id").ascending());

                    var resultPage = secUserRepository.findAll(pageable);

                    exchange.getMessage().setBody(resultPage.getContent());
                    exchange.setProperty("hasNext", resultPage.hasNext());
                    exchange.setProperty("page", page + 1);

                    log.info("Fetched page={}, size={}, returnedRows={}, hasNext={}",
                            page, PAGE_SIZE, resultPage.getNumberOfElements(), resultPage.hasNext());
                })
                .choice()
                .when(simple("${body.size} == 0"))
                .log("No rows in this page, continuing...")
                .otherwise()
                .split(body()).streaming()
                .log("Consuming sec_user mysqlId=${body.id}, username=${body.username}")
                .process(customerProcessor)
                .end()
                .endChoice()
                .end();
    }
}


