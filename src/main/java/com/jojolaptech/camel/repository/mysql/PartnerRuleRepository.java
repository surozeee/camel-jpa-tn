package com.jojolaptech.camel.repository.mysql;

import com.jojolaptech.camel.model.mysql.partner.PartnerRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartnerRuleRepository extends JpaRepository<PartnerRule, Long> {
}


