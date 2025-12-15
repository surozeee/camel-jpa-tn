package com.jojolaptech.camel.repository.mysql;

import com.jojolaptech.camel.model.mysql.partner.TenderPartnerCommission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TenderPartnerCommissionRepository extends JpaRepository<TenderPartnerCommission, Long> {
}


