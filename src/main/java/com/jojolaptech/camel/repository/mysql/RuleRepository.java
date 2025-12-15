package com.jojolaptech.camel.repository.mysql;

import com.jojolaptech.camel.model.mysql.partner.Rule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RuleRepository extends JpaRepository<Rule, Long> {
}


