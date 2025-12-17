package com.jojolaptech.camel.route;

import com.jojolaptech.camel.processor.CategoryProcessor;
import com.jojolaptech.camel.processor.CustomerProcessor;
import com.jojolaptech.camel.processor.NewsPaperProcessor;
import com.jojolaptech.camel.processor.UniqueCodeProcessor;
import com.jojolaptech.camel.processor.UserPaymentProcessor;
import com.jojolaptech.camel.repository.mysql.NewsPaperRepository;
import com.jojolaptech.camel.repository.mysql.UniqueCodeTableRepository;
import com.jojolaptech.camel.repository.mysql.UserPaymentRepository;
import com.jojolaptech.camel.repository.mysql.sec.SecUserRepository;
import com.jojolaptech.camel.repository.mysql.tendersystem.CategoryRepository;
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
    private final CategoryProcessor categoryProcessor;
    private final NewsPaperProcessor newsPaperProcessor;
    private final UniqueCodeProcessor uniqueCodeProcessor;
    private final UserPaymentProcessor userPaymentProcessor;
    private final SecUserRepository secUserRepository;
    private final CategoryRepository categoryRepository;
    private final NewsPaperRepository newsPaperRepository;
    private final UniqueCodeTableRepository uniqueCodeTableRepository;
    private final UserPaymentRepository userPaymentRepository;

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

        // Route for category migration
        from("timer:category-import?repeatCount=1&delay=2000")
                .routeId("category-migration")
                .setProperty("page").constant(0)
                .setProperty("hasNext").constant(true)

                .loopDoWhile(exchange -> Boolean.TRUE.equals(exchange.getProperty("hasNext", Boolean.class)))
                .process(exchange -> {
                    int page = exchange.getProperty("page", Integer.class);
                    var pageable = org.springframework.data.domain.PageRequest.of(page, PAGE_SIZE,
                            org.springframework.data.domain.Sort.by("id").ascending());

                    var resultPage = categoryRepository.findAll(pageable);

                    exchange.getMessage().setBody(resultPage.getContent());
                    exchange.setProperty("hasNext", resultPage.hasNext());
                    exchange.setProperty("page", page + 1);

                    log.info("Fetched category page={}, size={}, returnedRows={}, hasNext={}",
                            page, PAGE_SIZE, resultPage.getNumberOfElements(), resultPage.hasNext());
                })
                .choice()
                .when(simple("${body.size} == 0"))
                .log("No category rows in this page, continuing...")
                .otherwise()
                .split(body()).streaming()
                .log("Consuming category mysqlId=${body.id}, name=${body.categoryName}")
                .process(categoryProcessor)
                .end()
                .endChoice()
                .end();

        // Route for newspaper migration
        from("timer:newspaper-import?repeatCount=1&delay=3000")
                .routeId("newspaper-migration")
                .setProperty("page").constant(0)
                .setProperty("hasNext").constant(true)

                .loopDoWhile(exchange -> Boolean.TRUE.equals(exchange.getProperty("hasNext", Boolean.class)))
                .process(exchange -> {
                    int page = exchange.getProperty("page", Integer.class);
                    var pageable = org.springframework.data.domain.PageRequest.of(page, PAGE_SIZE,
                            org.springframework.data.domain.Sort.by("id").ascending());

                    var resultPage = newsPaperRepository.findAll(pageable);

                    exchange.getMessage().setBody(resultPage.getContent());
                    exchange.setProperty("hasNext", resultPage.hasNext());
                    exchange.setProperty("page", page + 1);

                    log.info("Fetched news_paper page={}, size={}, returnedRows={}, hasNext={}",
                            page, PAGE_SIZE, resultPage.getNumberOfElements(), resultPage.hasNext());
                })
                .choice()
                .when(simple("${body.size} == 0"))
                .log("No news_paper rows in this page, continuing...")
                .otherwise()
                .split(body()).streaming()
                .log("Consuming news_paper mysqlId=${body.id}, name=${body.name}")
                .process(newsPaperProcessor)
                .end()
                .endChoice()
                .end();

        // Route for unique code migration
        from("timer:unique-code-import?repeatCount=1&delay=5000")
                .routeId("unique-code-migration")
                .setProperty("page").constant(0)
                .setProperty("hasNext").constant(true)

                .loopDoWhile(exchange -> Boolean.TRUE.equals(exchange.getProperty("hasNext", Boolean.class)))
                .process(exchange -> {
                    int page = exchange.getProperty("page", Integer.class);
                    var pageable = org.springframework.data.domain.PageRequest.of(page, PAGE_SIZE,
                            org.springframework.data.domain.Sort.by("id").ascending());

                    var resultPage = uniqueCodeTableRepository.findAll(pageable);

                    exchange.getMessage().setBody(resultPage.getContent());
                    exchange.setProperty("hasNext", resultPage.hasNext());
                    exchange.setProperty("page", page + 1);

                    log.info("Fetched unique_code_table page={}, size={}, returnedRows={}, hasNext={}",
                            page, PAGE_SIZE, resultPage.getNumberOfElements(), resultPage.hasNext());
                })
                .choice()
                .when(simple("${body.size} == 0"))
                .log("No unique_code rows in this page, continuing...")
                .otherwise()
                .split(body()).streaming()
                .log("Consuming unique_code_table mysqlId=${body.id}, code=${body.uniqueCode}")
                .process(uniqueCodeProcessor)
                .end()
                .endChoice()
                .end();

        // Route for user_payment migration
        from("timer:user-payment-import?repeatCount=1&delay=10000")
                .routeId("user-payment-migration")
                .setProperty("page").constant(0)
                .setProperty("hasNext").constant(true)

                .loopDoWhile(exchange -> Boolean.TRUE.equals(exchange.getProperty("hasNext", Boolean.class)))
                .process(exchange -> {
                    int page = exchange.getProperty("page", Integer.class);
                    var pageable = org.springframework.data.domain.PageRequest.of(page, PAGE_SIZE,
                            org.springframework.data.domain.Sort.by("id").ascending());

                    var resultPage = userPaymentRepository.findAll(pageable);

                    exchange.getMessage().setBody(resultPage.getContent());
                    exchange.setProperty("hasNext", resultPage.hasNext());
                    exchange.setProperty("page", page + 1);

                    log.info("Fetched user_payment page={}, size={}, returnedRows={}, hasNext={}",
                            page, PAGE_SIZE, resultPage.getNumberOfElements(), resultPage.hasNext());
                })
                .choice()
                .when(simple("${body.size} == 0"))
                .log("No user_payment rows in this page, continuing...")
                .otherwise()
                .split(body()).streaming()
                .log("Consuming user_payment mysqlId=${body.id}, transactionId=${body.transactionId}")
                .process(userPaymentProcessor)
                .end()
                .endChoice()
                .end();
    }
}


