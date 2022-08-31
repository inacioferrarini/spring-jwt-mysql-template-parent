package com.inacioferrarini.templates.api.commons.core.models.entities;

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
        createdAt = Timestamp.from(ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")).toInstant());
        updatedAt = createdAt;
    }

    @PreUpdate
    public void onUpdate() {
        updatedAt = Timestamp.from(ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")).toInstant());
    }

}
