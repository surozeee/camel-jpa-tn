package com.jojolaptech.camel.repository.mysql;

import com.jojolaptech.camel.model.mysql.tendersystem.ProductService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductServiceRepository extends JpaRepository<ProductService, Long> {
}


