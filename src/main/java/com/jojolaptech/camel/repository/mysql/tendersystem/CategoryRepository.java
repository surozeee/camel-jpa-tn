package com.jojolaptech.camel.repository.mysql.tendersystem;

import com.jojolaptech.camel.model.mysql.tendersystem.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {}

