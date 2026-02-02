package com.jojolaptech.camel.repository.postgres.notice;

import com.jojolaptech.camel.model.postgres.notice.UserCallLogEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("pgUserCallLogRepository")
public interface UserCallLogRepository extends JpaRepository<UserCallLogEntity, UUID> {
    boolean existsByMysqlId(Long mysqlId);
}
