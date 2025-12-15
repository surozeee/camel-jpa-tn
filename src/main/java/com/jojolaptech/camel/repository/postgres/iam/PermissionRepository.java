package com.jojolaptech.camel.repository.postgres.iam;

import com.jojolaptech.camel.model.postgres.iam.PermissionEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("pgPermissionRepository")
public interface PermissionRepository extends JpaRepository<PermissionEntity, UUID> {}


