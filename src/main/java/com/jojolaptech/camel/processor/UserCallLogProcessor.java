package com.jojolaptech.camel.processor;

import com.jojolaptech.camel.model.mysql.tendersystem.CallLog;
import com.jojolaptech.camel.model.postgres.enums.CallActionEnum;
import com.jojolaptech.camel.model.postgres.iam.UserEntity;
import com.jojolaptech.camel.model.postgres.notice.UserCallLogEntity;
import com.jojolaptech.camel.repository.postgres.iam.UserRepository;
import com.jojolaptech.camel.repository.postgres.notice.UserCallLogRepository;
import lombok.RequiredArgsConstructor;
import org.apache.camel.Exchange;
import org.apache.camel.InvalidPayloadException;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserCallLogProcessor implements Processor {

    private static final Logger log = LoggerFactory.getLogger(UserCallLogProcessor.class);

    private final UserCallLogRepository userCallLogRepository;
    private final UserRepository userRepository;

    @Override
    public void process(Exchange exchange) throws InvalidPayloadException {
        CallLog source = exchange.getMessage().getMandatoryBody(CallLog.class);

        log.info("Migrating call_log id={} to user_call_logs", source.getId());

        if (userCallLogRepository.existsByMysqlId(source.getId())) {
            log.info("Skipping call_log id={}, already exists", source.getId());
            return;
        }

        UserEntity user = null;
        if (source.getSecUser() != null && source.getSecUser().getId() != null) {
            Optional<UserEntity> userOpt = userRepository.findByMysqlId(source.getSecUser().getId());
            if (userOpt.isPresent()) {
                user = userOpt.get();
                log.debug("Found user mysqlId={}, postgresId={}", source.getSecUser().getId(), user.getId());
            } else {
                log.warn("User not found in Postgres for mysqlId={}, skipping call_log id={}", source.getSecUser().getId(), source.getId());
                return;
            }
        } else {
            log.warn("CallLog id={} has no sec_user relationship, skipping", source.getId());
            return;
        }

        CallActionEnum action = mapCallTypeToAction(source.getCallType());

        UserCallLogEntity target = UserCallLogEntity.builder()
                .mysqlId(source.getId())
                .action(action)
                .remarks(source.getRemarks())
                .user(user)
                .build();

        UserCallLogEntity saved = userCallLogRepository.save(target);
        log.info("Saved user_call_logs to Postgres with id={}, mysqlId={}, userId={}",
                saved.getId(), saved.getMysqlId(), saved.getUser().getId());
    }

    private static CallActionEnum mapCallTypeToAction(com.jojolaptech.camel.model.mysql.enums.CallType callType) {
        if (callType == null) {
            return CallActionEnum.CALL;
        }
        return CallActionEnum.CALL;
    }
}
