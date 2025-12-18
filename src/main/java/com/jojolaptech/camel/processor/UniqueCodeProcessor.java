package com.jojolaptech.camel.processor;

import com.jojolaptech.camel.model.mysql.tendersystem.UniqueCodeTable;
import com.jojolaptech.camel.model.postgres.enums.UniqueCodeStatusEnum;
import com.jojolaptech.camel.model.postgres.iam.UserEntity;
import com.jojolaptech.camel.model.postgres.notice.UniqueCodeEntity;
import com.jojolaptech.camel.repository.postgres.iam.UserRepository;
import com.jojolaptech.camel.repository.postgres.notice.UniqueCodeRepository;
import lombok.RequiredArgsConstructor;
import org.apache.camel.Exchange;
import org.apache.camel.InvalidPayloadException;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UniqueCodeProcessor implements Processor {

    private static final Logger log = LoggerFactory.getLogger(UniqueCodeProcessor.class);

    private final UniqueCodeRepository uniqueCodeRepository;
    private final UserRepository userRepository;

    @Override
    public void process(Exchange exchange) throws InvalidPayloadException {
        UniqueCodeTable source = exchange.getMessage().getMandatoryBody(UniqueCodeTable.class);

        log.info("Migrating unique_code_table id={}, code={}", source.getId(), source.getUniqueCode());

        // Check if already exists
        if (uniqueCodeRepository.existsByMysqlId(source.getId())) {
            log.info("Skipping unique_code id={}, already exists", source.getId());
            return;
        }

        // Look up the user by mysqlId
        UUID userId = null;
        if (source.getSecUser() != null && source.getSecUser().getId() != null) {
            Optional<UserEntity> userOpt = userRepository.findByMysqlId(source.getSecUser().getId());
            
            if (userOpt.isPresent()) {
                userId = userOpt.get().getId();
                log.debug("Found user with mysqlId={}, postgresId={}", source.getSecUser().getId(), userId);
            } else {
                log.warn("User not found in Postgres for mysqlId={}", source.getSecUser().getId());
            }
        }

        UniqueCodeEntity target = new UniqueCodeEntity();
        target.setMysqlId(source.getId());
        target.setCode(source.getUniqueCode());
        target.setValidityDays(source.getNoOfDay());
        
        // Convert Instant to LocalDate
        if (source.getUserVerifyDate() != null) {
            LocalDate usedDate = source.getUserVerifyDate()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
            target.setUsedDate(usedDate);
        }
        
        // Map Boolean status to UniqueCodeStatusEnum
        if (source.getStatus() != null && source.getStatus()) {
            target.setStatus(UniqueCodeStatusEnum.USED);
        } else {
            target.setStatus(UniqueCodeStatusEnum.AVAILABLE);
        }
        
        target.setUserId(userId);

        UniqueCodeEntity saved = uniqueCodeRepository.save(target);
        log.info("Saved unique_code to Postgres with id={}, code={}", saved.getId(), saved.getCode());
    }
}

