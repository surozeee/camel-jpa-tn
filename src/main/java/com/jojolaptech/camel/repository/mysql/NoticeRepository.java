package com.jojolaptech.camel.repository.mysql;

import com.jojolaptech.camel.model.mysql.tendersystem.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> {
    
    @Query("SELECT n FROM Notice n LEFT JOIN FETCH n.industry LEFT JOIN FETCH n.productService LEFT JOIN FETCH n.category LEFT JOIN FETCH n.newsPaper LEFT JOIN FETCH n.tenderClassification")
    Page<Notice> findAllWithRelationships(Pageable pageable);
}


