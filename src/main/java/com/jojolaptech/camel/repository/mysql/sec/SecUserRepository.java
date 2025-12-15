package com.jojolaptech.camel.repository.mysql.sec;

import com.jojolaptech.camel.model.mysql.sec.SecUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SecUserRepository extends JpaRepository<SecUser, Long> {}

