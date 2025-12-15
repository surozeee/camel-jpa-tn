package com.jojolaptech.camel.repository.postgres.notice;

import com.jojolaptech.camel.model.postgres.notice.NoticeCategoryEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("pgNoticeCategoryRepository")
public interface NoticeCategoryRepository
        extends JpaRepository<NoticeCategoryEntity, UUID> {}


