package com.jojolaptech.camel.repository.postgres.notice;

import com.jojolaptech.camel.model.postgres.notice.NoticeBookmarkEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("pgNoticeBookmarkRepository")
public interface NoticeBookmarkRepository extends JpaRepository<NoticeBookmarkEntity, UUID> {
    boolean existsByMysqlId(Long mysqlId);
}

