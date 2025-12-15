package com.jojolaptech.camel.repository.postgres.storage;

import com.jojolaptech.camel.model.postgres.storage.NotificationMessageEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("pgNotificationMessageRepository")
public interface NotificationMessageRepository
        extends JpaRepository<NotificationMessageEntity, UUID> {}


