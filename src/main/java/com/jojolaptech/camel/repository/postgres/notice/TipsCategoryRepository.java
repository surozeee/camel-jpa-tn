package com.jojolaptech.camel.repository.postgres.notice;

import com.jojolaptech.camel.model.postgres.notice.TipsCategoryEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("pgTipsCategoryRepository")
public interface TipsCategoryRepository extends JpaRepository<TipsCategoryEntity, UUID> {
    java.util.Optional<TipsCategoryEntity> findByMysqlId(Long mysqlId);
}


