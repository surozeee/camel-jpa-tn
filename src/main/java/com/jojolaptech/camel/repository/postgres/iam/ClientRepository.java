package com.jojolaptech.camel.repository.postgres.iam;

import com.jojolaptech.camel.model.postgres.iam.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("pgClientRepository")
public interface ClientRepository extends JpaRepository<ClientEntity, String> {}


