package com.jojolaptech.camel.repository.postgres.iam;

import com.jojolaptech.camel.model.postgres.iam.UserInfoEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("pgUserInfoRepository")
public interface UserInfoRepository extends JpaRepository<UserInfoEntity, UUID> {
    java.util.Optional<UserInfoEntity> findByEmailAddress(String emailAddress);
    java.util.Optional<UserInfoEntity> findByUser_Id(UUID userId);
}


