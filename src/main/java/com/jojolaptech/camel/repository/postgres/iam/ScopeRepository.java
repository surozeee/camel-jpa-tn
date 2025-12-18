package com.jojolaptech.camel.repository.postgres.iam;

import com.jojolaptech.camel.model.postgres.iam.ScopeEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("pgScopeRepository")
public interface ScopeRepository extends JpaRepository<ScopeEntity, UUID> {
    boolean existsByName(String name);
}


