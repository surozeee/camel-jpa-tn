package com.jojolaptech.camel.repository.postgres.notice;

import com.jojolaptech.camel.model.postgres.notice.NewsPaperEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("pgNewsPaperRepository")
public interface NewsPaperRepository extends JpaRepository<NewsPaperEntity, UUID> {}


