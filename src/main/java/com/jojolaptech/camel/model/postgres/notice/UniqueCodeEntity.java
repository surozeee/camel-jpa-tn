package com.jojolaptech.camel.model.postgres.notice;


import com.jojolaptech.camel.model.postgres.BaseAuditEntity;
import com.jojolaptech.camel.model.postgres.enums.DateFormatEnum;
import com.jojolaptech.camel.model.postgres.enums.UniqueCodeStatusEnum;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "unique_code")
public class UniqueCodeEntity extends BaseAuditEntity {

    @Enumerated(EnumType.STRING)
    private DateFormatEnum dateFormat;
    private String code;
    private int validityDays;
    private LocalDate usedDate;

    @Enumerated(EnumType.STRING)
    private UniqueCodeStatusEnum status;

    private UUID userId;

}

