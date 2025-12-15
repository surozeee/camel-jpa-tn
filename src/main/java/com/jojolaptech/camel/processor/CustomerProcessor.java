package com.jojolaptech.camel.processor;

import com.jojolaptech.camel.model.mysql.SourceCustomer;
import com.jojolaptech.camel.model.mysql.sec.SecUser;
import com.jojolaptech.camel.model.postgres.TargetCustomer;
import com.jojolaptech.camel.model.postgres.enums.UserStatusEnum;
import com.jojolaptech.camel.model.postgres.iam.UserEntity;
import com.jojolaptech.camel.repository.mysql.SourceCustomerRepository;
import com.jojolaptech.camel.repository.postgres.TargetCustomerRepository;
import com.jojolaptech.camel.repository.postgres.iam.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.camel.Exchange;
import org.apache.camel.InvalidPayloadException;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerProcessor implements Processor {

    private final UserRepository targetCustomerRepository;
    private final SourceCustomerRepository sourceCustomerRepository;

    @Override
    public void process(Exchange exchange) throws InvalidPayloadException {
        SecUser source = exchange.getMessage().getMandatoryBody(SecUser.class);

        UserEntity target = new UserEntity();
        target.setUsername(source.getUsername());
        target.setStatus(source.getIsVerified() ? UserStatusEnum.ACTIVE : UserStatusEnum.PENDING);
        target.setVersion(0L);
        target.setPassword(source.getPassword());

        targetCustomerRepository.save(target);

//        source.setExported(true);
//        sourceCustomerRepository.save(source);
    }
}

