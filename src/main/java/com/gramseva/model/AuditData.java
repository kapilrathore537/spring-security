package com.gramseva.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Base class for auditing entity data, providing common fields such as createdDate, updatedDate, isDeleted, and isActive.
 * Uses JPA and Spring Data JPA annotations for automatic management of these fields.
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class AuditData {

    /**
     * The date and time when the entity was created.
     * Managed automatically by Spring Data JPA.
     */
    @CreatedDate
    @Column(nullable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdDate;

    /**
     * The date and time when the entity was last updated.
     * Managed automatically by Spring Data JPA.
     */
    @LastModifiedDate
    @Column(nullable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime updatedDate;

    
//    /**
//     * The user who created the entity.
//     * Managed automatically by Spring Data JPA.
//     */
//    @CreatedBy
//    @Column(nullable = false, columnDefinition = "VARCHAR(100) DEFAULT ''")
//    private String createdBy;
//
//    /**
//     * The user who last modified the entity.
//     * Managed automatically by Spring Data JPA.
//     */
//    @LastModifiedBy
//    @Column(nullable = false, columnDefinition = "VARCHAR(100) DEFAULT ''")
//    private String updatedBy;
    
    
    /**
     * Flag indicating whether the entity is deleted.
     * Defaults to {@code false}.
     */
    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean isDeleted = Boolean.FALSE;

    /**
     * Flag indicating whether the entity is active.
     * Defaults to {@code true}.
     */
    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean isActive = Boolean.TRUE;
}
