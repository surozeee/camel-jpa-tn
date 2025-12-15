package com.jojolaptech.camel.repository.mysql.sec;

import com.jojolaptech.camel.model.mysql.sec.SecondaryEmail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SecondaryEmailRepository extends JpaRepository<SecondaryEmail, Long> {}

