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
    private Timestamp created_at;

    @Column(columnDefinition="TIMESTAMP DEFAULT NULL")
    private Timestamp updated_at;

    @PrePersist
    public void onInsert() {
        created_at = Timestamp.from(ZonedDateTime.now(ZoneId.of("Asia/Kolkata")).toInstant());
        updated_at = created_at;
    }

    @PreUpdate
    public void onUpdate() {
        updated_at = Timestamp.from(ZonedDateTime.now(ZoneId.of("Asia/Kolkata")).toInstant());
    }


//    @CreationTimestamp
//    @Column(name="created_at", nullable = false, updatable = false, insertable = false)
//    private Timestamp createdAt;
//
//    @UpdateTimestamp
//    @Column(name="updated_at", nullable = false, updatable = false, insertable = false)
//    private Timestamp updatedAt;

}
