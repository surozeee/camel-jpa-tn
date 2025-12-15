package com.jojolaptech.camel.repository.mysql;

import com.jojolaptech.camel.model.mysql.partner.PartnerGeneralCommission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartnerGeneralCommissionRepository extends JpaRepository<PartnerGeneralCommission, Long> {
}


