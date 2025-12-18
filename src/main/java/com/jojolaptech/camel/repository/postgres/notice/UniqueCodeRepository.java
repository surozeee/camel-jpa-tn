package com.jojolaptech.camel.repository.postgres.notice;

import com.jojolaptech.camel.model.postgres.notice.UniqueCodeEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("pgUniqueCodeRepository")
public interface UniqueCodeRepository extends JpaRepository<UniqueCodeEntity, UUID> {
    boolean existsByMysqlId(Long mysqlId);
}


