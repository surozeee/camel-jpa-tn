package com.jojolaptech.camel.repository.postgres.iam;

import com.jojolaptech.camel.model.postgres.iam.RoleEntity;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("pgRoleRepository")
public interface RoleRepository extends JpaRepository<RoleEntity, UUID> {
    boolean existsByName(String name);

    RoleEntity findByName(String name);
}


