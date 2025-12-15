package com.jojolaptech.camel.repository.mysql;

import com.jojolaptech.camel.model.mysql.tendersystem.UserInformations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInformationsRepository extends JpaRepository<UserInformations, Long> {
}


