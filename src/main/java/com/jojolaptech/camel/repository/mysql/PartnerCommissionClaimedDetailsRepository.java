package com.jojolaptech.camel.repository.mysql;

import com.jojolaptech.camel.model.mysql.partner.PartnerCommissionClaimedDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartnerCommissionClaimedDetailsRepository extends JpaRepository<PartnerCommissionClaimedDetails, Long> {
}


