package com.jojolaptech.camel.repository.postgres.iam;

import com.jojolaptech.camel.model.postgres.iam.PartnerSpecificCommissionEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartnerSpecificCommissionRepository
        extends JpaRepository<PartnerSpecificCommissionEntity, UUID> {}


