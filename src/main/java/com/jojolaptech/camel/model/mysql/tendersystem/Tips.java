package com.jojolaptech.camel.model.mysql.tendersystem;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.Instant;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tips")
@Getter
@Setter
public class Tips {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tip_note", columnDefinition = "text")
    private String tipNote;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "added_date")
    private Instant addedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tips_category_id")
    private TipsCategory tipsCategory;
}

