package com.jojolaptech.camel.repository.postgres.notice;

import com.jojolaptech.camel.model.postgres.notice.ProductServiceEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("pgProductServiceRepository")
public interface ProductServiceRepository
        extends JpaRepository<ProductServiceEntity, UUID> {
    java.util.Optional<ProductServiceEntity> findByMysqlId(Long mysqlId);
    boolean existsByMysqlId(Long mysqlId);
}


