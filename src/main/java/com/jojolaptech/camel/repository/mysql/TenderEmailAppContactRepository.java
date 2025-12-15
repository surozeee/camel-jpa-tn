package com.jojolaptech.camel.repository.mysql;

import com.jojolaptech.camel.model.mysql.tendersystem.TenderEmailAppContact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TenderEmailAppContactRepository extends JpaRepository<TenderEmailAppContact, Long> {
}


