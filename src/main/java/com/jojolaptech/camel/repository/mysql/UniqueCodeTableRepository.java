package com.jojolaptech.camel.repository.mysql;

import com.jojolaptech.camel.model.mysql.tendersystem.UniqueCodeTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UniqueCodeTableRepository extends JpaRepository<UniqueCodeTable, Long> {
}


