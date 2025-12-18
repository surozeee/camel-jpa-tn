package com.jojolaptech.camel.repository.mysql;

import com.jojolaptech.camel.model.mysql.sec.SecUser;
import com.jojolaptech.camel.model.mysql.tendersystem.UserInformations;
import com.jojolaptech.camel.model.postgres.iam.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInformationsRepository extends JpaRepository<UserInformations, Long> {

    UserInformations findFirstBySecUser_Id(Long secUserId);

    UserInformations findFirstBySecUser(SecUser user);
}


