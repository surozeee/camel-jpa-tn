package com.jojolaptech.camel.repository.postgres.notice;

import com.jojolaptech.camel.model.postgres.notice.QuotedAmountEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("pgQuotedAmountRepository")
public interface QuotedAmountRepository extends JpaRepository<QuotedAmountEntity, UUID> {}


