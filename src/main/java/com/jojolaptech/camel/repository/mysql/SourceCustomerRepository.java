package com.jojolaptech.camel.repository.mysql;

import com.jojolaptech.camel.model.mysql.SourceCustomer;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SourceCustomerRepository extends JpaRepository<SourceCustomer, Long> {

    List<SourceCustomer> findTop50ByExportedFalseOrExportedIsNullOrderByIdAsc();
}

