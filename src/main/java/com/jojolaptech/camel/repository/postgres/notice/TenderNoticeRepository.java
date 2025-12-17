package com.jojolaptech.camel.repository.postgres.notice;

import com.jojolaptech.camel.model.postgres.notice.TenderNoticeEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("pgTenderNoticeRepository")
public interface TenderNoticeRepository extends JpaRepository<TenderNoticeEntity, UUID> {
    java.util.Optional<TenderNoticeEntity> findByMysqlId(Long mysqlId);
}


