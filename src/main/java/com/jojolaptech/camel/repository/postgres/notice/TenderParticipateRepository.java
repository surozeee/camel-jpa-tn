package com.jojolaptech.camel.repository.postgres.notice;

import com.jojolaptech.camel.model.postgres.notice.TenderParticipateEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("pgTenderParticipateRepository")
public interface TenderParticipateRepository
        extends JpaRepository<TenderParticipateEntity, UUID> {}


