package com.jojolaptech.camel.repository.postgres;

import com.jojolaptech.camel.model.postgres.TargetCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("pgTargetCustomerRepository")
public interface TargetCustomerRepository extends JpaRepository<TargetCustomer, Long> {
}

