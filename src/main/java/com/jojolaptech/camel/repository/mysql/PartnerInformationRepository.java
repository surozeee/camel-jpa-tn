package com.jojolaptech.camel.repository.mysql;

import com.jojolaptech.camel.model.mysql.partner.PartnerInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartnerInformationRepository extends JpaRepository<PartnerInformation, Long> {
}


