package com.jojolaptech.camel.repository.postgres.iam;

import com.jojolaptech.camel.model.postgres.iam.UserEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("pgUserRepository")
public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    java.util.Optional<UserEntity> findByMysqlId(Long mysqlId);
    java.util.Optional<UserEntity> findByUsername(String username);
    boolean existsByMysqlId(Long mysqlId);
}


