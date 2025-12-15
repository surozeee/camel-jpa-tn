package com.jojolaptech.camel.repository.postgres.storage;

import com.jojolaptech.camel.model.postgres.storage.CustomNotificationEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("pgCustomNotificationRepository")
public interface CustomNotificationRepository
        extends JpaRepository<CustomNotificationEntity, UUID> {}


