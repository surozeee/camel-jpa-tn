package com.jojolaptech.camel.processor;

import com.jojolaptech.camel.model.mysql.tendersystem.NewsletterSubscription;
import com.jojolaptech.camel.model.postgres.iam.EmailSubscriptionEntity;
import com.jojolaptech.camel.model.postgres.iam.UserEntity;
import com.jojolaptech.camel.model.postgres.iam.UserInfoEntity;
import com.jojolaptech.camel.repository.postgres.iam.EmailSubscriptionRepository;
import com.jojolaptech.camel.repository.postgres.iam.UserInfoRepository;
import com.jojolaptech.camel.repository.postgres.iam.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.camel.Exchange;
import org.apache.camel.InvalidPayloadException;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class NewsletterSubscriptionProcessor implements Processor {

    private static final Logger log = LoggerFactory.getLogger(NewsletterSubscriptionProcessor.class);

    private final EmailSubscriptionRepository emailSubscriptionRepository;
    private final UserRepository userRepository;
    private final UserInfoRepository userInfoRepository;

    @Override
    public void process(Exchange exchange) throws InvalidPayloadException {
        NewsletterSubscription source = exchange.getMessage().getMandatoryBody(NewsletterSubscription.class);

        log.info("Migrating newsletter_subscription id={}, email={}", source.getId(), source.getEmailAddress());

        // Check if already exists
        if (emailSubscriptionRepository.existsByMysqlId(source.getId())) {
            log.info("Skipping newsletter_subscription id={}, already exists", source.getId());
            return;
        }

        // Find user by email address (username in UserEntity or emailAddress in UserInfoEntity)
        UserInfoEntity userInfo = null;
        
        // First try to find by username (which is email in the source schema)
        Optional<UserEntity> userOpt = userRepository.findByUsername(source.getEmailAddress());
        
        if (userOpt.isPresent()) {
            UserEntity user = userOpt.get();
            // Find the UserInfoEntity for this user
            Optional<UserInfoEntity> userInfoOpt = userInfoRepository.findByUser_Id(user.getId());
            
            if (userInfoOpt.isPresent()) {
                userInfo = userInfoOpt.get();
                log.debug("Found userInfo for email={}, userId={}", source.getEmailAddress(), user.getId());
            } else {
                log.warn("UserInfo not found for user with email={}", source.getEmailAddress());
            }
        } else {
            // Try to find by emailAddress in UserInfoEntity
            Optional<UserInfoEntity> userInfoOpt = userInfoRepository.findByEmailAddress(source.getEmailAddress());
            
            if (userInfoOpt.isPresent()) {
                userInfo = userInfoOpt.get();
                log.debug("Found userInfo by emailAddress={}", source.getEmailAddress());
            } else {
                log.warn("No user found for email={}, creating subscription without user link", source.getEmailAddress());
            }
        }

        EmailSubscriptionEntity target = new EmailSubscriptionEntity();
        target.setMysqlId(source.getId());
        target.setSubscribed(source.getIsSubscribed() != null && source.getIsSubscribed());
        target.setNewsletterEnabled(source.getIsSubscribed() != null && source.getIsSubscribed());
        
        // Convert Instant to LocalDateTime
        if (source.getUnsubscribeDate() != null) {
            LocalDateTime unsubscribeDate = source.getUnsubscribeDate()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime();
            target.setUnsubscribeDate(unsubscribeDate);
        }
        
        // Link to UserInfoEntity if found
        if (userInfo != null) {
            target.setUserInfo(userInfo);
        }

        EmailSubscriptionEntity saved = emailSubscriptionRepository.save(target);
        log.info("Saved email_subscription to Postgres with id={}, email={}", saved.getId(), source.getEmailAddress());
    }
}

