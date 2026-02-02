package com.jojolaptech.camel.repository.mysql.tendersystem;

import com.jojolaptech.camel.model.mysql.tendersystem.CallLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CallLogRepository extends JpaRepository<CallLog, Long> {

    @Query("SELECT cl FROM CallLog cl LEFT JOIN FETCH cl.secUser")
    Page<CallLog> findAllWithSecUser(Pageable pageable);
}

