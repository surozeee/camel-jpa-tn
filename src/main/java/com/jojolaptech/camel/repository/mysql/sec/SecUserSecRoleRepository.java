package com.jojolaptech.camel.repository.mysql.sec;

import com.jojolaptech.camel.model.mysql.sec.SecUserSecRole;
import com.jojolaptech.camel.model.mysql.sec.SecUserSecRoleId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SecUserSecRoleRepository
        extends JpaRepository<SecUserSecRole, SecUserSecRoleId> {}

