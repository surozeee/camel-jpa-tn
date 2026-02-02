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
import com.jojolaptech.camel.processor.UserCallLogProcessor;
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
import com.jojolaptech.camel.repository.mysql.tendersystem.CallLogRepository;
import com.jojolaptech.camel.repository.mysql.tendersystem.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
    private final UserCallLogProcessor userCallLogProcessor;
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
    private final CallLogRepository callLogRepository;
    private final PayPlanRepository payPlanRepository;

    // Optimized page size: balance between memory usage and database round trips
    // 100 records per page reduces queries by 20x compared to 5, while keeping memory manageable
    private static final int PAGE_SIZE = 100;
    
    // Throttle delay between migrations to allow GC and prevent memory buildup
    private static final int MIGRATION_THROTTLE_MS = 1000;

    @Override
    public void configure() {

        errorHandler(defaultErrorHandler()
                .maximumRedeliveries(3)
                .redeliveryDelay(2000));

        // Master route that triggers all migrations sequentially
        from("timer:master-import?repeatCount=1&delay=0")
                .routeId("master-migration-route")
                .process(exchange -> {
                    long startTime = System.currentTimeMillis();
                    exchange.setProperty("startTime", startTime);
                    LocalDateTime startDateTime = LocalDateTime.now();
                    exchange.setProperty("startDateTime", startDateTime);
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
                    log.info("==========================================");
                    log.info("Starting sequential migration process...");
                    log.info("Start Time: {}", startDateTime.format(formatter));
                    log.info("Page Size: {} (optimized for performance)", PAGE_SIZE);
                    log.info("==========================================");
                })
                .to("direct:mysql-to-postgres-import")
                .log("Step 1 completed: mysql-to-postgres-import")
                .process(exchange -> {
                    // Allow GC between migrations
                    System.gc();
                    Thread.sleep(MIGRATION_THROTTLE_MS);
                })
                .to("direct:category-migration")
                .log("Step 2 completed: category-migration")
                .process(exchange -> { System.gc(); Thread.sleep(MIGRATION_THROTTLE_MS); })
                .to("direct:tips-category-migration")
                .log("Step 3 completed: tips-category-migration")
                .process(exchange -> { System.gc(); Thread.sleep(MIGRATION_THROTTLE_MS); })
                .to("direct:tips-migration")
                .log("Step 4 completed: tips-migration")
                .process(exchange -> { System.gc(); Thread.sleep(MIGRATION_THROTTLE_MS); })
                .to("direct:product-service-migration")
                .log("Step 5 completed: product-service-migration")
                .process(exchange -> { System.gc(); Thread.sleep(MIGRATION_THROTTLE_MS); })
                .to("direct:tender-classification-migration")
                .log("Step 6 completed: tender-classification-migration")
                .process(exchange -> { System.gc(); Thread.sleep(MIGRATION_THROTTLE_MS); })
                .to("direct:newspaper-migration")
                .log("Step 7 completed: newspaper-migration")
                .process(exchange -> { System.gc(); Thread.sleep(MIGRATION_THROTTLE_MS); })
                .to("direct:industry-migration")
                .log("Step 8 completed: industry-migration")
                .process(exchange -> { System.gc(); Thread.sleep(MIGRATION_THROTTLE_MS); })
                .to("direct:notice-migration")
                .log("Step 9 completed: notice-migration")
                .process(exchange -> { System.gc(); Thread.sleep(MIGRATION_THROTTLE_MS); })
                .to("direct:tag-migration")
                .log("Step 10 completed: tag-migration")
                .process(exchange -> { System.gc(); Thread.sleep(MIGRATION_THROTTLE_MS); })
                .to("direct:user-notes-migration")
                .log("Step 11 completed: user-notes-migration")
                .process(exchange -> { System.gc(); Thread.sleep(MIGRATION_THROTTLE_MS); })
                .to("direct:newsletter-subscription-migration")
                .log("Step 12 completed: newsletter-subscription-migration")
                .process(exchange -> { System.gc(); Thread.sleep(MIGRATION_THROTTLE_MS); })
                .to("direct:unique-code-migration")
                .log("Step 13 completed: unique-code-migration")
                .process(exchange -> { System.gc(); Thread.sleep(MIGRATION_THROTTLE_MS); })
                .to("direct:pay-plan-migration")
                .log("Step 14 completed: pay-plan-migration")
                .process(exchange -> { System.gc(); Thread.sleep(MIGRATION_THROTTLE_MS); })
                .to("direct:user-payment-migration")
                .log("Step 15 completed: user-payment-migration")
                .process(exchange -> { System.gc(); Thread.sleep(MIGRATION_THROTTLE_MS); })
                .to("direct:call-log-migration")
                .log("Step 16 completed: call-log-migration")
                .process(exchange -> {
                    long endTime = System.currentTimeMillis();
                    long startTime = exchange.getProperty("startTime", Long.class);
                    long totalTime = endTime - startTime;
                    LocalDateTime endDateTime = LocalDateTime.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
                    
                    long hours = totalTime / (1000 * 60 * 60);
                    long minutes = (totalTime % (1000 * 60 * 60)) / (1000 * 60);
                    long seconds = (totalTime % (1000 * 60)) / 1000;
                    long milliseconds = totalTime % 1000;
                    
                    // Force final GC before logging
                    System.gc();
                    
                    log.info("==========================================");
                    log.info("All migrations completed successfully!");
                    log.info("Start Time: {}", exchange.getProperty("startDateTime", LocalDateTime.class).format(formatter));
                    log.info("End Time: {}", endDateTime.format(formatter));
                    log.info("Total Time: {} hours, {} minutes, {} seconds, {} milliseconds", hours, minutes, seconds, milliseconds);
                    log.info("Total Time (ms): {} ms", totalTime);
                    log.info("==========================================");
                    log.info("IMPORT SUMMARY - Records imported per table:");
                    log.info("--------------------------------------------");
                    log.info("1.  sec_user (mysql-to-postgres-import):        {}", exchange.getProperty("secUserCount", 0, Integer.class));
                    log.info("2.  category:                                   {}", exchange.getProperty("categoryCount", 0, Integer.class));
                    log.info("3.  tips_category:                              {}", exchange.getProperty("tipsCategoryCount", 0, Integer.class));
                    log.info("4.  tips:                                       {}", exchange.getProperty("tipsCount", 0, Integer.class));
                    log.info("5.  product_service:                           {}", exchange.getProperty("productServiceCount", 0, Integer.class));
                    log.info("6.  tender_classification:                      {}", exchange.getProperty("tenderClassificationCount", 0, Integer.class));
                    log.info("7.  news_paper:                                 {}", exchange.getProperty("newspaperCount", 0, Integer.class));
                    log.info("8.  industry:                                   {}", exchange.getProperty("industryCount", 0, Integer.class));
                    log.info("9.  notice:                                     {}", exchange.getProperty("noticeCount", 0, Integer.class));
                    log.info("10. tag:                                        {}", exchange.getProperty("tagCount", 0, Integer.class));
                    log.info("11. user_notes (notice_bookmark):               {}", exchange.getProperty("userNotesCount", 0, Integer.class));
                    log.info("12. newsletter_subscription:                    {}", exchange.getProperty("newsletterSubscriptionCount", 0, Integer.class));
                    log.info("13. unique_code:                                {}", exchange.getProperty("uniqueCodeCount", 0, Integer.class));
                    log.info("14. pay_plan (payment_rule):                    {}", exchange.getProperty("payPlanCount", 0, Integer.class));
                    log.info("15. user_payment:                               {}", exchange.getProperty("userPaymentCount", 0, Integer.class));
                    log.info("16. call_log (user_call_logs):                    {}", exchange.getProperty("callLogCount", 0, Integer.class));
                    
                    int grandTotal = (exchange.getProperty("secUserCount", 0, Integer.class) +
                                     exchange.getProperty("categoryCount", 0, Integer.class) +
                                     exchange.getProperty("tipsCategoryCount", 0, Integer.class) +
                                     exchange.getProperty("tipsCount", 0, Integer.class) +
                                     exchange.getProperty("productServiceCount", 0, Integer.class) +
                                     exchange.getProperty("tenderClassificationCount", 0, Integer.class) +
                                     exchange.getProperty("newspaperCount", 0, Integer.class) +
                                     exchange.getProperty("industryCount", 0, Integer.class) +
                                     exchange.getProperty("noticeCount", 0, Integer.class) +
                                     exchange.getProperty("tagCount", 0, Integer.class) +
                                     exchange.getProperty("userNotesCount", 0, Integer.class) +
                                     exchange.getProperty("newsletterSubscriptionCount", 0, Integer.class) +
                                     exchange.getProperty("uniqueCodeCount", 0, Integer.class) +
                                     exchange.getProperty("payPlanCount", 0, Integer.class) +
                                     exchange.getProperty("userPaymentCount", 0, Integer.class) +
                                     exchange.getProperty("callLogCount", 0, Integer.class));
                    
                    log.info("--------------------------------------------");
                    log.info("GRAND TOTAL:                                   {}", grandTotal);
                    log.info("==========================================");
                });

        // Route for mysql-to-postgres-import (sec_user)
        from("direct:mysql-to-postgres-import")
                .routeId("mysql-to-postgres-import")
                .setProperty("page").constant(0)
                .setProperty("hasNext").constant(true)
                .setProperty("importCount").constant(0)

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
                .split(body()).streaming().stopOnException()
                .log("Consuming sec_user mysqlId=${body.id}, username=${body.username}")
                .process(exchange -> {
                    Integer count = exchange.getProperty("importCount", Integer.class);
                    exchange.setProperty("importCount", count + 1);
                })
                .process(customerProcessor)
                .end()
                .endChoice()
                .end()
                .process(exchange -> {
                    Integer totalCount = exchange.getProperty("importCount", Integer.class);
                    log.info("==========================================");
                    log.info("mysql-to-postgres-import (sec_user) completed!");
                    log.info("Total records imported: {}", totalCount);
                    log.info("==========================================");
                    exchange.setProperty("secUserCount", totalCount);
                });

        // Route for category migration
        from("direct:category-migration")
                .routeId("category-migration")
                .setProperty("page").constant(0)
                .setProperty("hasNext").constant(true)
                .setProperty("importCount").constant(0)

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
                .split(body()).streaming().stopOnException()
                .log("Consuming category mysqlId=${body.id}, name=${body.categoryName}")
                .process(exchange -> {
                    Integer count = exchange.getProperty("importCount", Integer.class);
                    exchange.setProperty("importCount", count + 1);
                })
                .process(categoryProcessor)
                .end()
                .endChoice()
                .end()
                .process(exchange -> {
                    Integer totalCount = exchange.getProperty("importCount", Integer.class);
                    log.info("==========================================");
                    log.info("category-migration completed!");
                    log.info("Total records imported: {}", totalCount);
                    log.info("==========================================");
                    exchange.setProperty("categoryCount", totalCount);
                });

        // Route for tips_category migration
        from("direct:tips-category-migration")
                .routeId("tips-category-migration")
                .setProperty("page").constant(0)
                .setProperty("hasNext").constant(true)
                .setProperty("importCount").constant(0)

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
                .split(body()).streaming().stopOnException()
                .log("Consuming tips_category mysqlId=${body.id}, name=${body.tipCategory}")
                .process(exchange -> {
                    Integer count = exchange.getProperty("importCount", Integer.class);
                    exchange.setProperty("importCount", count + 1);
                })
                .process(tipsCategoryProcessor)
                .end()
                .endChoice()
                .end()
                .process(exchange -> {
                    Integer totalCount = exchange.getProperty("importCount", Integer.class);
                    log.info("==========================================");
                    log.info("tips-category-migration completed!");
                    log.info("Total records imported: {}", totalCount);
                    log.info("==========================================");
                    exchange.setProperty("tipsCategoryCount", totalCount);
                });

        // Route for tips migration
        from("direct:tips-migration")
                .routeId("tips-migration")
                .setProperty("page").constant(0)
                .setProperty("hasNext").constant(true)
                .setProperty("importCount").constant(0)

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
                .split(body()).streaming().stopOnException()
                .log("Consuming tips mysqlId=${body.id}")
                .process(exchange -> {
                    Integer count = exchange.getProperty("importCount", Integer.class);
                    exchange.setProperty("importCount", count + 1);
                })
                .process(tipsProcessor)
                .end()
                .endChoice()
                .end()
                .process(exchange -> {
                    Integer totalCount = exchange.getProperty("importCount", Integer.class);
                    log.info("==========================================");
                    log.info("tips-migration completed!");
                    log.info("Total records imported: {}", totalCount);
                    log.info("==========================================");
                    exchange.setProperty("tipsCount", totalCount);
                });

        // Route for product_service migration to product_service
        from("direct:product-service-migration")
                .routeId("product-service-migration")
                .setProperty("page").constant(0)
                .setProperty("hasNext").constant(true)
                .setProperty("importCount").constant(0)

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
                .split(body()).streaming().stopOnException()
                .log("Consuming product_service mysqlId=${body.id}, name=${body.name}")
                .process(exchange -> {
                    Integer count = exchange.getProperty("importCount", Integer.class);
                    exchange.setProperty("importCount", count + 1);
                })
                .process(productServiceProcessor)
                .end()
                .endChoice()
                .end()
                .process(exchange -> {
                    Integer totalCount = exchange.getProperty("importCount", Integer.class);
                    log.info("==========================================");
                    log.info("product-service-migration completed!");
                    log.info("Total records imported: {}", totalCount);
                    log.info("==========================================");
                    exchange.setProperty("productServiceCount", totalCount);
                });

        // Route for tender_classification migration to district
        from("direct:tender-classification-migration")
                .routeId("tender-classification-migration")
                .setProperty("page").constant(0)
                .setProperty("hasNext").constant(true)
                .setProperty("importCount").constant(0)

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
                .split(body()).streaming().stopOnException()
                .log("Consuming tender_classification mysqlId=${body.id}, name=${body.classificationName}")
                .process(exchange -> {
                    Integer count = exchange.getProperty("importCount", Integer.class);
                    exchange.setProperty("importCount", count + 1);
                })
                .process(tenderClassificationProcessor)
                .end()
                .endChoice()
                .end()
                .process(exchange -> {
                    Integer totalCount = exchange.getProperty("importCount", Integer.class);
                    log.info("==========================================");
                    log.info("tender-classification-migration completed!");
                    log.info("Total records imported: {}", totalCount);
                    log.info("==========================================");
                    exchange.setProperty("tenderClassificationCount", totalCount);
                });

        // Route for newspaper migration
        from("direct:newspaper-migration")
                .routeId("newspaper-migration")
                .setProperty("page").constant(0)
                .setProperty("hasNext").constant(true)
                .setProperty("importCount").constant(0)

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
                .split(body()).streaming().stopOnException()
                .log("Consuming news_paper mysqlId=${body.id}, name=${body.name}")
                .process(exchange -> {
                    Integer count = exchange.getProperty("importCount", Integer.class);
                    exchange.setProperty("importCount", count + 1);
                })
                .process(newsPaperProcessor)
                .end()
                .endChoice()
                .end()
                .process(exchange -> {
                    Integer totalCount = exchange.getProperty("importCount", Integer.class);
                    log.info("==========================================");
                    log.info("newspaper-migration completed!");
                    log.info("Total records imported: {}", totalCount);
                    log.info("==========================================");
                    exchange.setProperty("newspaperCount", totalCount);
                });

        // Route for industry migration
        from("direct:industry-migration")
                .routeId("industry-migration")
                .setProperty("page").constant(0)
                .setProperty("hasNext").constant(true)
                .setProperty("importCount").constant(0)

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
                .split(body()).streaming().stopOnException()
                .log("Consuming industry mysqlId=${body.id}, name=${body.name}")
                .process(exchange -> {
                    Integer count = exchange.getProperty("importCount", Integer.class);
                    exchange.setProperty("importCount", count + 1);
                })
                .process(industryProcessor)
                .end()
                .endChoice()
                .end()
                .process(exchange -> {
                    Integer totalCount = exchange.getProperty("importCount", Integer.class);
                    log.info("==========================================");
                    log.info("industry-migration completed!");
                    log.info("Total records imported: {}", totalCount);
                    log.info("==========================================");
                    exchange.setProperty("industryCount", totalCount);
                });

        //TODO update and fix
        // Route for notice migration to tender_notice
        from("direct:notice-migration")
                .routeId("notice-migration")
                .setProperty("page").constant(0)
                .setProperty("hasNext").constant(true)
                .setProperty("importCount").constant(0)

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
                .split(body()).streaming().stopOnException()
                .log("Consuming notice mysqlId=${body.id}, provider=${body.noticeProvider}")
                .process(exchange -> {
                    Integer count = exchange.getProperty("importCount", Integer.class);
                    exchange.setProperty("importCount", count + 1);
                })
                .process(noticeProcessor)
                .end()
                .endChoice()
                .end()
                .process(exchange -> {
                    Integer totalCount = exchange.getProperty("importCount", Integer.class);
                    log.info("==========================================");
                    log.info("notice-migration completed!");
                    log.info("Total records imported: {}", totalCount);
                    log.info("==========================================");
                    exchange.setProperty("noticeCount", totalCount);
                });

        // Route for tag migration to tags
        from("direct:tag-migration")
                .routeId("tag-migration")
                .setProperty("page").constant(0)
                .setProperty("hasNext").constant(true)
                .setProperty("importCount").constant(0)

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
                .split(body()).streaming().stopOnException()
                .log("Consuming tag mysqlId=${body.id}, name=${body.name}")
                .process(exchange -> {
                    Integer count = exchange.getProperty("importCount", Integer.class);
                    exchange.setProperty("importCount", count + 1);
                })
                .process(tagProcessor)
                .end()
                .endChoice()
                .end()
                .process(exchange -> {
                    Integer totalCount = exchange.getProperty("importCount", Integer.class);
                    log.info("==========================================");
                    log.info("tag-migration completed!");
                    log.info("Total records imported: {}", totalCount);
                    log.info("==========================================");
                    exchange.setProperty("tagCount", totalCount);
                });

        // Route for user_notes migration to notice_bookmark
        from("direct:user-notes-migration")
                .routeId("user-notes-migration")
                .setProperty("page").constant(0)
                .setProperty("hasNext").constant(true)
                .setProperty("importCount").constant(0)

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
                .split(body()).streaming().stopOnException()
                .log("Consuming user_notes mysqlId=${body.id}")
                .process(exchange -> {
                    Integer count = exchange.getProperty("importCount", Integer.class);
                    exchange.setProperty("importCount", count + 1);
                })
                .process(noticeBookmarkProcessor)
                .end()
                .endChoice()
                .end()
                .process(exchange -> {
                    Integer totalCount = exchange.getProperty("importCount", Integer.class);
                    log.info("==========================================");
                    log.info("user-notes-migration completed!");
                    log.info("Total records imported: {}", totalCount);
                    log.info("==========================================");
                    exchange.setProperty("userNotesCount", totalCount);
                });

        // Route for newsletter subscription migration
        from("direct:newsletter-subscription-migration")
                .routeId("newsletter-subscription-migration")
                .setProperty("page").constant(0)
                .setProperty("hasNext").constant(true)
                .setProperty("importCount").constant(0)

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
                .split(body()).streaming().stopOnException()
                .log("Consuming newsletter_subscription mysqlId=${body.id}, email=${body.emailAddress}")
                .process(exchange -> {
                    Integer count = exchange.getProperty("importCount", Integer.class);
                    exchange.setProperty("importCount", count + 1);
                })
                .process(newsletterSubscriptionProcessor)
                .end()
                .endChoice()
                .end()
                .process(exchange -> {
                    Integer totalCount = exchange.getProperty("importCount", Integer.class);
                    log.info("==========================================");
                    log.info("newsletter-subscription-migration completed!");
                    log.info("Total records imported: {}", totalCount);
                    log.info("==========================================");
                    exchange.setProperty("newsletterSubscriptionCount", totalCount);
                });

        // Route for unique code migration
        from("direct:unique-code-migration")
                .routeId("unique-code-migration")
                .setProperty("page").constant(0)
                .setProperty("hasNext").constant(true)
                .setProperty("importCount").constant(0)

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
                .split(body()).streaming().stopOnException()
                .log("Consuming unique_code_table mysqlId=${body.id}, code=${body.uniqueCode}")
                .process(exchange -> {
                    Integer count = exchange.getProperty("importCount", Integer.class);
                    exchange.setProperty("importCount", count + 1);
                })
                .process(uniqueCodeProcessor)
                .end()
                .endChoice()
                .end()
                .process(exchange -> {
                    Integer totalCount = exchange.getProperty("importCount", Integer.class);
                    log.info("==========================================");
                    log.info("unique-code-migration completed!");
                    log.info("Total records imported: {}", totalCount);
                    log.info("==========================================");
                    exchange.setProperty("uniqueCodeCount", totalCount);
                });

        // Route for pay_plan migration to payment_rule
        from("direct:pay-plan-migration")
                .routeId("pay-plan-migration")
                .setProperty("page").constant(0)
                .setProperty("hasNext").constant(true)
                .setProperty("importCount").constant(0)

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
                .split(body()).streaming().stopOnException()
                .log("Consuming pay_plan mysqlId=${body.id}, period=${body.period}")
                .process(exchange -> {
                    Integer count = exchange.getProperty("importCount", Integer.class);
                    exchange.setProperty("importCount", count + 1);
                })
                .process(payPlanProcessor)
                .end()
                .endChoice()
                .end()
                .process(exchange -> {
                    Integer totalCount = exchange.getProperty("importCount", Integer.class);
                    log.info("==========================================");
                    log.info("pay-plan-migration completed!");
                    log.info("Total records imported: {}", totalCount);
                    log.info("==========================================");
                    exchange.setProperty("payPlanCount", totalCount);
                });

        // Route for user_payment migration
        from("direct:user-payment-migration")
                .routeId("user-payment-migration")
                .setProperty("page").constant(0)
                .setProperty("hasNext").constant(true)
                .setProperty("importCount").constant(0)

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
                .split(body()).streaming().stopOnException()
                .log("Consuming user_payment mysqlId=${body.id}, transactionId=${body.transactionId}")
                .process(exchange -> {
                    Integer count = exchange.getProperty("importCount", Integer.class);
                    exchange.setProperty("importCount", count + 1);
                })
                .process(userPaymentProcessor)
                .end()
                .endChoice()
                .end()
                .process(exchange -> {
                    Integer totalCount = exchange.getProperty("importCount", Integer.class);
                    log.info("==========================================");
                    log.info("user-payment-migration completed!");
                    log.info("Total records imported: {}", totalCount);
                    log.info("==========================================");
                    exchange.setProperty("userPaymentCount", totalCount);
                });

        // Route for call_log migration to user_call_logs
        from("direct:call-log-migration")
                .routeId("call-log-migration")
                .setProperty("page").constant(0)
                .setProperty("hasNext").constant(true)
                .setProperty("importCount").constant(0)

                .loopDoWhile(exchange -> Boolean.TRUE.equals(exchange.getProperty("hasNext", Boolean.class)))
                .process(exchange -> {
                    int page = exchange.getProperty("page", Integer.class);
                    var pageable = PageRequest.of(page, PAGE_SIZE,
                            Sort.by("id").ascending());

                    var resultPage = callLogRepository.findAllWithSecUser(pageable);

                    exchange.getMessage().setBody(resultPage.getContent());
                    exchange.setProperty("hasNext", resultPage.hasNext());
                    exchange.setProperty("page", page + 1);

                    log.info("Fetched call_log page={}, size={}, returnedRows={}, hasNext={}",
                            page, PAGE_SIZE, resultPage.getNumberOfElements(), resultPage.hasNext());
                })
                .choice()
                .when(simple("${body.size} == 0"))
                .log("No call_log rows in this page, continuing...")
                .otherwise()
                .split(body()).streaming().stopOnException()
                .log("Consuming call_log mysqlId=${body.id}")
                .process(exchange -> {
                    Integer count = exchange.getProperty("importCount", Integer.class);
                    exchange.setProperty("importCount", count + 1);
                })
                .process(userCallLogProcessor)
                .end()
                .endChoice()
                .end()
                .process(exchange -> {
                    Integer totalCount = exchange.getProperty("importCount", Integer.class);
                    log.info("==========================================");
                    log.info("call-log-migration completed!");
                    log.info("Total records imported: {}", totalCount);
                    log.info("==========================================");
                    exchange.setProperty("callLogCount", totalCount);
                });
    }
}


