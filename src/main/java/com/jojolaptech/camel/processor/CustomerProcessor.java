package com.jojolaptech.camel.processor;

import com.jojolaptech.camel.model.mysql.sec.SecUser;
import com.jojolaptech.camel.model.mysql.tendersystem.UserInformations;
import com.jojolaptech.camel.model.mysql.tendersystem.UserPayment;
import com.jojolaptech.camel.model.postgres.enums.UserStatusEnum;
import com.jojolaptech.camel.model.postgres.iam.RoleEntity;
import com.jojolaptech.camel.model.postgres.iam.UserEntity;
import com.jojolaptech.camel.model.postgres.iam.UserInfoEntity;
import com.jojolaptech.camel.repository.mysql.UserInformationsRepository;
import com.jojolaptech.camel.repository.mysql.UserPaymentRepository;
import com.jojolaptech.camel.repository.postgres.iam.RoleRepository;
import com.jojolaptech.camel.repository.postgres.iam.UserInfoRepository;
import com.jojolaptech.camel.repository.postgres.iam.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.camel.Exchange;
import org.apache.camel.InvalidPayloadException;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class CustomerProcessor implements Processor {

    private static final Logger log = LoggerFactory.getLogger(CustomerProcessor.class);

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserInfoRepository userInfoRepository;
    private final UserInformationsRepository userInformationsRepository;
    private final UserPaymentRepository userPaymentRepository;

    @Override
    public void process(Exchange exchange) throws InvalidPayloadException {
        SecUser source = exchange.getMessage().getMandatoryBody(SecUser.class);

        log.info("Migrating sec_user id={}, username={}", source.getId(), source.getUsername());

        UserEntity user = new UserEntity();
        user.setMysqlId(source.getId());
        user.setUsername(source.getUsername());
        user.setStatus(Boolean.TRUE.equals(source.getIsVerified()) ? UserStatusEnum.ACTIVE : UserStatusEnum.PENDING);
        user.setVersion(0L);
        user.setPassword(source.getPassword());

//        UserEntity savedUser = userRepository.save(user);
//        log.info("Saved user to Postgres with id={}", savedUser.getId());
        RoleEntity role = roleRepository.findByName("CUSTOMER");
        user.setRoles(Set.of(role));


        UserInformations userInformations = userInformationsRepository.findBySecUser(source);
        
        // Only create UserInfoEntity if userInformations exists
        if (userInformations != null) {
            UserInfoEntity userInfoEntity = mapUserInfo(user, userInformations);
            UserPayment userPayment = userPaymentRepository.findFirstBySecUserOrderByExpireDateDesc(source);
            userInfoEntity.setSubscriptionExpiryDate(
                    userPayment != null && userPayment.getExpireDate() != null
                            ? LocalDateTime.ofInstant(
                            userPayment.getExpireDate(),
                            ZoneId.systemDefault()
                    )
                            : null
            );
            user.setUserInfo(userInfoEntity);
        } else {
            log.warn("No UserInformations found for sec_user id={}, skipping user info mapping", source.getId());
        }

        userRepository.save(user);
    }

    private UserInfoEntity mapUserInfo(UserEntity savedUser, UserInformations info) {
        UserInfoEntity targetInfo = new UserInfoEntity();
        targetInfo.setUser(savedUser);
        targetInfo.setName(info.getName());
        targetInfo.setAddress(info.getAddress());
        targetInfo.setMobileNumber(info.getMobile());
        targetInfo.setTelePhoneNumber(info.getTel());
        targetInfo.setProfileImage(info.getImageName());
        // Username is an email in the source schema; reuse it for email address.
        targetInfo.setEmailAddress(savedUser.getUsername());
        return targetInfo;
    }
}

