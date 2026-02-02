package com.jojolaptech.camel.repository.postgres.notice;

import com.tendernotice.tenderservice.entity.TenderParticipateEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("pgTenderParticipateRepository")
public interface TenderParticipateRepository
        extends JpaRepository<TenderParticipateEntity, UUID> {}


