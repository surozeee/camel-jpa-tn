package com.jojolaptech.camel.repository.postgres.iam;

import com.jojolaptech.camel.model.postgres.iam.ResourceEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("pgResourceRepository")
public interface ResourceRepository extends JpaRepository<ResourceEntity, UUID> {}


