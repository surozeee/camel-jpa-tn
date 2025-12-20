package com.jojolaptech.camel.route;

import com.jojolaptech.camel.processor.CategoryProcessor;
import com.jojolaptech.camel.processor.CustomerProcessor;
import com.jojolaptech.camel.processor.IndustryProcessor;
import com.jojolaptech.camel.processor.NewsPaperProcessor;
import com.jojolaptech.camel.processor.NewsletterSubscriptionProcessor;
import com.jojolaptech.camel.processor.NoticeProcessor;
import com.jojolaptech.camel.processor.ProductServiceProcessor;
import com.jojolaptech.camel.processor.TagProcessor;
import com.jojolaptech.camel.processor.NoticeBookmarkProcessor;
import com.jojolaptech.camel.processor.TenderClassificationProcessor;
import com.jojolaptech.camel.processor.TipsCategoryProcessor;
import com.jojolaptech.camel.processor.TipsProcessor;
import com.jojolaptech.camel.processor.UniqueCodeProcessor;
import com.jojolaptech.camel.processor.PayPlanProcessor;
import com.jojolaptech.camel.processor.UserPaymentProcessor;
import com.jojolaptech.camel.repository.mysql.IndustryRepository;
import com.jojolaptech.camel.repository.mysql.NewsletterSubscriptionRepository;
import com.jojolaptech.camel.repository.mysql.NewsPaperRepository;
import com.jojolaptech.camel.repository.mysql.NoticeRepository;
import com.jojolaptech.camel.repository.mysql.ProductServiceRepository;
import com.jojolaptech.camel.repository.mysql.TagRepository;
import com.jojolaptech.camel.repository.mysql.UserNotesRepository;
import com.jojolaptech.camel.repository.mysql.TenderClassificationRepository;
import com.jojolaptech.camel.repository.mysql.TipsCategoryRepository;
import com.jojolaptech.camel.repository.mysql.TipsRepository;
import com.jojolaptech.camel.repository.mysql.UniqueCodeTableRepository;
import com.jojolaptech.camel.repository.mysql.PayPlanRepository;
import com.jojolaptech.camel.repository.mysql.UserPaymentRepository;
import com.jojolaptech.camel.repository.mysql.sec.SecUserRepository;
import com.jojolaptech.camel.repository.mysql.tendersystem.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ImportRouteBuilder extends RouteBuilder {

    private static final Logger log = LoggerFactory.getLogger(ImportRouteBuilder.class);

    private final CustomerProcessor customerProcessor;
    private final CategoryProcessor categoryProcessor;
    private final ProductServiceProcessor productServiceProcessor;
    private final TenderClassificationProcessor tenderClassificationProcessor;
    private final TipsCategoryProcessor tipsCategoryProcessor;
    private final TipsProcessor tipsProcessor;
    private final NewsPaperProcessor newsPaperProcessor;
    private final IndustryProcessor industryProcessor;
    private final NoticeProcessor noticeProcessor;
    private final TagProcessor tagProcessor;
    private final NoticeBookmarkProcessor noticeBookmarkProcessor;
    private final NewsletterSubscriptionProcessor newsletterSubscriptionProcessor;
    private final UniqueCodeProcessor uniqueCodeProcessor;
    private final UserPaymentProcessor userPaymentProcessor;
    private final PayPlanProcessor payPlanProcessor;
    private final SecUserRepository secUserRepository;
    private final CategoryRepository categoryRepository;
    private final ProductServiceRepository productServiceRepository;
    private final TenderClassificationRepository tenderClassificationRepository;
    private final TipsCategoryRepository tipsCategoryRepository;
    private final TipsRepository tipsRepository;
    private final NewsPaperRepository newsPaperRepository;
    private final IndustryRepository industryRepository;
    private final NoticeRepository noticeRepository;
    private final TagRepository tagRepository;
    private final UserNotesRepository userNotesRepository;
    private final NewsletterSubscriptionRepository newsletterSubscriptionRepository;
    private final UniqueCodeTableRepository uniqueCodeTableRepository;
    private final UserPaymentRepository userPaymentRepository;
    private final PayPlanRepository payPlanRepository;

    private static final int PAGE_SIZE = 5; // tune this

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
                    var pageable = PageRequest.of(page, PAGE_SIZE,
                            Sort.by("id").ascending());

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
        /*from("timer:category-import?repeatCount=1&delay=2000")
                .routeId("category-migration")
                .setProperty("page").constant(0)
                .setProperty("hasNext").constant(true)

                .loopDoWhile(exchange -> Boolean.TRUE.equals(exchange.getProperty("hasNext", Boolean.class)))
                .process(exchange -> {
                    int page = exchange.getProperty("page", Integer.class);
                    var pageable = PageRequest.of(page, PAGE_SIZE,
                            Sort.by("id").ascending());

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

        // Route for tips_category migration
        from("timer:tips-category-import?repeatCount=1&delay=2200")
                .routeId("tips-category-migration")
                .setProperty("page").constant(0)
                .setProperty("hasNext").constant(true)

                .loopDoWhile(exchange -> Boolean.TRUE.equals(exchange.getProperty("hasNext", Boolean.class)))
                .process(exchange -> {
                    int page = exchange.getProperty("page", Integer.class);
                    var pageable = PageRequest.of(page, PAGE_SIZE,
                            Sort.by("id").ascending());

                    var resultPage = tipsCategoryRepository.findAll(pageable);

                    exchange.getMessage().setBody(resultPage.getContent());
                    exchange.setProperty("hasNext", resultPage.hasNext());
                    exchange.setProperty("page", page + 1);

                    log.info("Fetched tips_category page={}, size={}, returnedRows={}, hasNext={}",
                            page, PAGE_SIZE, resultPage.getNumberOfElements(), resultPage.hasNext());
                })
                .choice()
                .when(simple("${body.size} == 0"))
                .log("No tips_category rows in this page, continuing...")
                .otherwise()
                .split(body()).streaming()
                .log("Consuming tips_category mysqlId=${body.id}, name=${body.tipCategory}")
                .process(tipsCategoryProcessor)
                .end()
                .endChoice()
                .end();*/

        // Route for tips migration
        /*from("timer:tips-import?repeatCount=1&delay=2400")
                .routeId("tips-migration")
                .setProperty("page").constant(0)
                .setProperty("hasNext").constant(true)

                .loopDoWhile(exchange -> Boolean.TRUE.equals(exchange.getProperty("hasNext", Boolean.class)))
                .process(exchange -> {
                    int page = exchange.getProperty("page", Integer.class);
                    var pageable = PageRequest.of(page, PAGE_SIZE,
                            Sort.by("id").ascending());

                    var resultPage = tipsRepository.findAll(pageable);

                    exchange.getMessage().setBody(resultPage.getContent());
                    exchange.setProperty("hasNext", resultPage.hasNext());
                    exchange.setProperty("page", page + 1);

                    log.info("Fetched tips page={}, size={}, returnedRows={}, hasNext={}",
                            page, PAGE_SIZE, resultPage.getNumberOfElements(), resultPage.hasNext());
                })
                .choice()
                .when(simple("${body.size} == 0"))
                .log("No tips rows in this page, continuing...")
                .otherwise()
                .split(body()).streaming()
                .log("Consuming tips mysqlId=${body.id}")
                .process(tipsProcessor)
                .end()
                .endChoice()
                .end();*/

        // Route for product_service migration to product_service
        /*from("timer:product-service-import?repeatCount=1&delay=2500")
                .routeId("product-service-migration")
                .setProperty("page").constant(0)
                .setProperty("hasNext").constant(true)

                .loopDoWhile(exchange -> Boolean.TRUE.equals(exchange.getProperty("hasNext", Boolean.class)))
                .process(exchange -> {
                    int page = exchange.getProperty("page", Integer.class);
                    var pageable = PageRequest.of(page, PAGE_SIZE,
                            Sort.by("id").ascending());

                    var resultPage = productServiceRepository.findAll(pageable);

                    exchange.getMessage().setBody(resultPage.getContent());
                    exchange.setProperty("hasNext", resultPage.hasNext());
                    exchange.setProperty("page", page + 1);

                    log.info("Fetched product_service page={}, size={}, returnedRows={}, hasNext={}",
                            page, PAGE_SIZE, resultPage.getNumberOfElements(), resultPage.hasNext());
                })
                .choice()
                .when(simple("${body.size} == 0"))
                .log("No product_service rows in this page, continuing...")
                .otherwise()
                .split(body()).streaming()
                .log("Consuming product_service mysqlId=${body.id}, name=${body.name}")
                .process(productServiceProcessor)
                .end()
                .endChoice()
                .end();*/

        // Route for tender_classification migration to district
        /*from("timer:tender-classification-import?repeatCount=1&delay=2800")
                .routeId("tender-classification-migration")
                .setProperty("page").constant(0)
                .setProperty("hasNext").constant(true)

                .loopDoWhile(exchange -> Boolean.TRUE.equals(exchange.getProperty("hasNext", Boolean.class)))
                .process(exchange -> {
                    int page = exchange.getProperty("page", Integer.class);
                    var pageable = PageRequest.of(page, PAGE_SIZE,
                            Sort.by("id").ascending());

                    var resultPage = tenderClassificationRepository.findAll(pageable);

                    exchange.getMessage().setBody(resultPage.getContent());
                    exchange.setProperty("hasNext", resultPage.hasNext());
                    exchange.setProperty("page", page + 1);

                    log.info("Fetched tender_classification page={}, size={}, returnedRows={}, hasNext={}",
                            page, PAGE_SIZE, resultPage.getNumberOfElements(), resultPage.hasNext());
                })
                .choice()
                .when(simple("${body.size} == 0"))
                .log("No tender_classification rows in this page, continuing...")
                .otherwise()
                .split(body()).streaming()
                .log("Consuming tender_classification mysqlId=${body.id}, name=${body.classificationName}")
                .process(tenderClassificationProcessor)
                .end()
                .endChoice()
                .end();*/

        // Route for newspaper migration
        /*from("timer:newspaper-import?repeatCount=1&delay=3000")
                .routeId("newspaper-migration")
                .setProperty("page").constant(0)
                .setProperty("hasNext").constant(true)

                .loopDoWhile(exchange -> Boolean.TRUE.equals(exchange.getProperty("hasNext", Boolean.class)))
                .process(exchange -> {
                    int page = exchange.getProperty("page", Integer.class);
                    var pageable = PageRequest.of(page, PAGE_SIZE,
                            Sort.by("id").ascending());

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
                .end();*/

        // Route for industry migration
        /*from("timer:industry-import?repeatCount=1&delay=4000")
                .routeId("industry-migration")
                .setProperty("page").constant(0)
                .setProperty("hasNext").constant(true)

                .loopDoWhile(exchange -> Boolean.TRUE.equals(exchange.getProperty("hasNext", Boolean.class)))
                .process(exchange -> {
                    int page = exchange.getProperty("page", Integer.class);
                    var pageable = PageRequest.of(page, PAGE_SIZE,
                            Sort.by("id").ascending());

                    var resultPage = industryRepository.findAll(pageable);

                    exchange.getMessage().setBody(resultPage.getContent());
                    exchange.setProperty("hasNext", resultPage.hasNext());
                    exchange.setProperty("page", page + 1);

                    log.info("Fetched industry page={}, size={}, returnedRows={}, hasNext={}",
                            page, PAGE_SIZE, resultPage.getNumberOfElements(), resultPage.hasNext());
                })
                .choice()
                .when(simple("${body.size} == 0"))
                .log("No industry rows in this page, continuing...")
                .otherwise()
                .split(body()).streaming()
                .log("Consuming industry mysqlId=${body.id}, name=${body.name}")
                .process(industryProcessor)
                .end()
                .endChoice()
                .end();*/

        //TODO update and fix
        // Route for notice migration to tender_notice
        /*from("timer:notice-import?repeatCount=1&delay=6000")
                .routeId("notice-migration")
                .setProperty("page").constant(0)
                .setProperty("hasNext").constant(true)

                .loopDoWhile(exchange -> Boolean.TRUE.equals(exchange.getProperty("hasNext", Boolean.class)))
                .process(exchange -> {
                    int page = exchange.getProperty("page", Integer.class);
                    var pageable = PageRequest.of(page, PAGE_SIZE,
                            Sort.by("id").ascending());

                    var resultPage = noticeRepository.findAllByIdGreaterThan(pageable, 0);

                    exchange.getMessage().setBody(resultPage.getContent());
                    exchange.setProperty("hasNext", resultPage.hasNext());
                    exchange.setProperty("page", page + 1);

                    log.info("Fetched notice page={}, size={}, returnedRows={}, hasNext={}",
                            page, PAGE_SIZE, resultPage.getNumberOfElements(), resultPage.hasNext());
                })
                .choice()
                .when(simple("${body.size} == 0"))
                .log("No notice rows in this page, continuing...")
                .otherwise()
                .split(body()).streaming()
                .log("Consuming notice mysqlId=${body.id}, provider=${body.noticeProvider}")
                .process(noticeProcessor)
                .end()
                .endChoice()
                .end();*/

        // Route for tag migration to tags
        /*from("timer:tag-import?repeatCount=1&delay=7000")
                .routeId("tag-migration")
                .setProperty("page").constant(0)
                .setProperty("hasNext").constant(true)

                .loopDoWhile(exchange -> Boolean.TRUE.equals(exchange.getProperty("hasNext", Boolean.class)))
                .process(exchange -> {
                    int page = exchange.getProperty("page", Integer.class);
                    var pageable = PageRequest.of(page, PAGE_SIZE,
                            Sort.by("id").ascending());

                    var resultPage = tagRepository.findAll(pageable);

                    exchange.getMessage().setBody(resultPage.getContent());
                    exchange.setProperty("hasNext", resultPage.hasNext());
                    exchange.setProperty("page", page + 1);

                    log.info("Fetched tag page={}, size={}, returnedRows={}, hasNext={}",
                            page, PAGE_SIZE, resultPage.getNumberOfElements(), resultPage.hasNext());
                })
                .choice()
                .when(simple("${body.size} == 0"))
                .log("No tag rows in this page, continuing...")
                .otherwise()
                .split(body()).streaming()
                .log("Consuming tag mysqlId=${body.id}, name=${body.name}")
                .process(tagProcessor)
                .end()
                .endChoice()
                .end();*/

        // Route for user_notes migration to notice_bookmark
        /*from("timer:user-notes-import?repeatCount=1&delay=8000")
                .routeId("user-notes-migration")
                .setProperty("page").constant(0)
                .setProperty("hasNext").constant(true)

                .loopDoWhile(exchange -> Boolean.TRUE.equals(exchange.getProperty("hasNext", Boolean.class)))
                .process(exchange -> {
                    int page = exchange.getProperty("page", Integer.class);
                    var pageable = PageRequest.of(page, PAGE_SIZE,
                            Sort.by("id").ascending());

                    var resultPage = userNotesRepository.findAllWithRelationships(pageable);

                    exchange.getMessage().setBody(resultPage.getContent());
                    exchange.setProperty("hasNext", resultPage.hasNext());
                    exchange.setProperty("page", page + 1);

                    log.info("Fetched user_notes page={}, size={}, returnedRows={}, hasNext={}",
                            page, PAGE_SIZE, resultPage.getNumberOfElements(), resultPage.hasNext());
                })
                .choice()
                .when(simple("${body.size} == 0"))
                .log("No user_notes rows in this page, continuing...")
                .otherwise()
                .split(body()).streaming()
                .log("Consuming user_notes mysqlId=${body.id}")
                .process(noticeBookmarkProcessor)
                .end()
                .endChoice()
                .end();*/

        // Route for newsletter subscription migration
        /*from("timer:newsletter-subscription-import?repeatCount=1&delay=15000")
                .routeId("newsletter-subscription-migration")
                .setProperty("page").constant(0)
                .setProperty("hasNext").constant(true)

                .loopDoWhile(exchange -> Boolean.TRUE.equals(exchange.getProperty("hasNext", Boolean.class)))
                .process(exchange -> {
                    int page = exchange.getProperty("page", Integer.class);
                    var pageable = PageRequest.of(page, PAGE_SIZE,
                            Sort.by("id").ascending());

                    var resultPage = newsletterSubscriptionRepository.findAll(pageable);

                    exchange.getMessage().setBody(resultPage.getContent());
                    exchange.setProperty("hasNext", resultPage.hasNext());
                    exchange.setProperty("page", page + 1);

                    log.info("Fetched newsletter_subscription page={}, size={}, returnedRows={}, hasNext={}",
                            page, PAGE_SIZE, resultPage.getNumberOfElements(), resultPage.hasNext());
                })
                .choice()
                .when(simple("${body.size} == 0"))
                .log("No newsletter_subscription rows in this page, continuing...")
                .otherwise()
                .split(body()).streaming()
                .log("Consuming newsletter_subscription mysqlId=${body.id}, email=${body.emailAddress}")
                .process(newsletterSubscriptionProcessor)
                .end()
                .endChoice()
                .end();*/

        // Route for unique code migration
        /*from("timer:unique-code-import?repeatCount=1&delay=5000")
                .routeId("unique-code-migration")
                .setProperty("page").constant(0)
                .setProperty("hasNext").constant(true)

                .loopDoWhile(exchange -> Boolean.TRUE.equals(exchange.getProperty("hasNext", Boolean.class)))
                .process(exchange -> {
                    int page = exchange.getProperty("page", Integer.class);
                    var pageable = PageRequest.of(page, PAGE_SIZE,
                            Sort.by("id").ascending());

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
                .end();*/

        // Route for pay_plan migration to payment_rule
        /*from("timer:pay-plan-import?repeatCount=1&delay=8000")
                .routeId("pay-plan-migration")
                .setProperty("page").constant(0)
                .setProperty("hasNext").constant(true)

                .loopDoWhile(exchange -> Boolean.TRUE.equals(exchange.getProperty("hasNext", Boolean.class)))
                .process(exchange -> {
                    int page = exchange.getProperty("page", Integer.class);
                    var pageable = PageRequest.of(page, PAGE_SIZE,
                            Sort.by("id").ascending());

                    var resultPage = payPlanRepository.findAll(pageable);

                    exchange.getMessage().setBody(resultPage.getContent());
                    exchange.setProperty("hasNext", resultPage.hasNext());
                    exchange.setProperty("page", page + 1);

                    log.info("Fetched pay_plan page={}, size={}, returnedRows={}, hasNext={}",
                            page, PAGE_SIZE, resultPage.getNumberOfElements(), resultPage.hasNext());
                })
                .choice()
                .when(simple("${body.size} == 0"))
                .log("No pay_plan rows in this page, continuing...")
                .otherwise()
                .split(body()).streaming()
                .log("Consuming pay_plan mysqlId=${body.id}, period=${body.period}")
                .process(payPlanProcessor)
                .end()
                .endChoice()
                .end();*/

        // Route for user_payment migration
        /*from("timer:user-payment-import?repeatCount=1&delay=10000")
                .routeId("user-payment-migration")
                .setProperty("page").constant(0)
                .setProperty("hasNext").constant(true)

                .loopDoWhile(exchange -> Boolean.TRUE.equals(exchange.getProperty("hasNext", Boolean.class)))
                .process(exchange -> {
                    int page = exchange.getProperty("page", Integer.class);
                    var pageable = PageRequest.of(page, PAGE_SIZE,
                            Sort.by("id").ascending());

                    var resultPage = userPaymentRepository.findAllWithPayPlan(pageable);

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
                .end();*/
    }
}


