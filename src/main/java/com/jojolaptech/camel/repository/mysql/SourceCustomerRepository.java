package com.jojolaptech.camel.repository.mysql;

import com.jojolaptech.camel.model.mysql.SourceCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SourceCustomerRepository extends JpaRepository<SourceCustomer, Long> {

    List<SourceCustomer> findTop50ByExportedFalseOrExportedIsNullOrderByIdAsc();
}

