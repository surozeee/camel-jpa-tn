package com.jojolaptech.camel.repository.mysql;

import com.jojolaptech.camel.model.mysql.partner.PartnerGeneralCommissionUsd;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartnerGeneralCommissionUsdRepository extends JpaRepository<PartnerGeneralCommissionUsd, Long> {
}


