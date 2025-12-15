package com.jojolaptech.camel.model.storage.entity;

import com.tendernotice.core.entity.BaseAuditEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "document")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class DocumentEntity extends BaseAuditEntity {

    private String filename;
    private String contentType;
    private String originalFileName;
    private String extension;
    private String checkSum;
    private Long fileSize;
    private String bucketName;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String path;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime uploadedAt;

}
