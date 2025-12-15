package com.jojolaptech.camel.repository.postgres.storage;

import com.jojolaptech.camel.model.postgres.storage.TemplateEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TemplateRepository extends JpaRepository<TemplateEntity, UUID> {}


