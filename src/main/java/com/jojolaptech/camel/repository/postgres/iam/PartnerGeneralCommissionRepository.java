package com.jojolaptech.camel.repository.postgres.iam;

import com.jojolaptech.camel.model.postgres.iam.PartnerGeneralCommissionEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("pgPartnerGeneralCommissionRepository")
public interface PartnerGeneralCommissionRepository
        extends JpaRepository<PartnerGeneralCommissionEntity, UUID> {}


