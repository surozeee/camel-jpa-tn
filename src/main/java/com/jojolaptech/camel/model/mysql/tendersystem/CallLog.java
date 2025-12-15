package com.jojolaptech.camel.model.mysql.tendersystem;

import com.jojolaptech.camel.model.mysql.sec.SecUser;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "call_log")
@Getter
@Setter
public class CallLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "called_date")
    private Instant calledDate;

    @Column(columnDefinition = "text")
    private String remarks;

    @Enumerated(EnumType.STRING)
    @Column(name = "call_type")
    private CallType callType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sec_user_id")
    private SecUser secUser;
}

