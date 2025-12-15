package com.jojolaptech.camel.repository.postgres.iam;

import com.jojolaptech.camel.model.postgres.iam.ReferralUserEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("pgReferralUserRepository")
public interface ReferralUserRepository
        extends JpaRepository<ReferralUserEntity, UUID> {}


