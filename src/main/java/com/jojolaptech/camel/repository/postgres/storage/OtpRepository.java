package com.jojolaptech.camel.repository.postgres.storage;

import com.jojolaptech.camel.model.postgres.storage.OtpEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("pgOtpRepository")
public interface OtpRepository extends JpaRepository<OtpEntity, UUID> {}


