package com.jojolaptech.camel.repository.mysql;

import com.jojolaptech.camel.model.mysql.tendersystem.UserNotes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserNotesRepository extends JpaRepository<UserNotes, Long> {
    
    @Query("SELECT un FROM UserNotes un LEFT JOIN FETCH un.secUser LEFT JOIN FETCH un.notice")
    Page<UserNotes> findAllWithRelationships(Pageable pageable);
}


