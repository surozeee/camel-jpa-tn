package com.jojolaptech.camel.processor;

import com.jojolaptech.camel.model.mysql.sec.SecUser;
import com.jojolaptech.camel.model.mysql.tendersystem.UserInformations;
import com.jojolaptech.camel.model.postgres.enums.UserStatusEnum;
import com.jojolaptech.camel.model.postgres.iam.UserEntity;
import com.jojolaptech.camel.model.postgres.iam.UserInfoEntity;
import com.jojolaptech.camel.repository.mysql.UserInformationsRepository;
import com.jojolaptech.camel.repository.postgres.iam.UserInfoRepository;
import com.jojolaptech.camel.repository.postgres.iam.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.camel.Exchange;
import org.apache.camel.InvalidPayloadException;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
@RequiredArgsConstructor
public class CustomerProcessor implements Processor {

    private static final Logger log = LoggerFactory.getLogger(CustomerProcessor.class);

    private final UserRepository userRepository;
    private final UserInfoRepository userInfoRepository;
    private final UserInformationsRepository userInformationsRepository;

    @Override
    public void process(Exchange exchange) throws InvalidPayloadException {
        SecUser source = exchange.getMessage().getMandatoryBody(SecUser.class);

        log.info("Migrating sec_user id={}, username={}", source.getId(), source.getUsername());

        UserEntity user = new UserEntity();
        user.setUsername(source.getUsername());
        user.setStatus(Boolean.TRUE.equals(source.getIsVerified()) ? UserStatusEnum.ACTIVE : UserStatusEnum.PENDING);
        user.setVersion(0L);
        user.setPassword(source.getPassword());

        UserEntity savedUser = userRepository.save(user);
        log.info("Saved user to Postgres with id={}", savedUser.getId());

        userInformationsRepository
                .findFirstBySecUser_Id(source.getId())
                .ifPresentOrElse(
                        info -> {
                            UserInfoEntity targetInfo = mapUserInfo(savedUser, info);
                            userInfoRepository.save(targetInfo);
                            log.info("Saved user_info for sec_user id={}", source.getId());
                        },
                        () -> log.info("No user_informations row found for sec_user id={}", source.getId()));

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

