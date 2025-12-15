package com.jojolaptech.camel.repository.postgres.notice;

import com.jojolaptech.camel.model.postgres.notice.DistrictEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("pgDistrictRepository")
public interface DistrictRepository extends JpaRepository<DistrictEntity, UUID> {}


