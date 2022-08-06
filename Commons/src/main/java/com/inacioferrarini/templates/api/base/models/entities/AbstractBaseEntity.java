package com.inacioferrarini.templates.api.base.models.entities;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@MappedSuperclass
public abstract class AbstractBaseEntity {

    @Column(updatable = false, columnDefinition="TIMESTAMP DEFAULT NULL")
    private Timestamp createdAt;

    @Column(columnDefinition="TIMESTAMP DEFAULT NULL")
    private Timestamp updatedAt;

    @PrePersist
    public void onInsert() {
        createdAt = Timestamp.from(ZonedDateTime.now(ZoneId.of("Asia/Kolkata")).toInstant());
        updatedAt = createdAt;
    }

    @PreUpdate
    public void onUpdate() {
        updatedAt = Timestamp.from(ZonedDateTime.now(ZoneId.of("Asia/Kolkata")).toInstant());
    }

}
