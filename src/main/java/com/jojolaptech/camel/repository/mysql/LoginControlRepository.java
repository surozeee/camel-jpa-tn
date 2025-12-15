package com.jojolaptech.camel.repository.mysql;

import com.jojolaptech.camel.model.mysql.tendersystem.LoginControl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginControlRepository extends JpaRepository<LoginControl, Long> {
}


