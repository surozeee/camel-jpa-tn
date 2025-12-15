package com.jojolaptech.camel.repository.postgres.notice;

import com.jojolaptech.camel.model.postgres.notice.TipsEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("pgTipsRepository")
public interface TipsRepository extends JpaRepository<TipsEntity, UUID> {}


