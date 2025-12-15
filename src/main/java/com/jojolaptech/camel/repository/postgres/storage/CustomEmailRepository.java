package com.jojolaptech.camel.repository.postgres.storage;

import com.jojolaptech.camel.model.postgres.storage.CustomEmailEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("pgCustomEmailRepository")
public interface CustomEmailRepository extends JpaRepository<CustomEmailEntity, UUID> {}


