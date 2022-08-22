package com.inacioferrarini.templates.api.security.models.entities;

import com.inacioferrarini.templates.api.base.models.entities.AbstractBaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity(name = "security_tokens")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class SecurityTokenEntity extends AbstractBaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity owner;

    private String token;

    @Column(columnDefinition="TIMESTAMP DEFAULT NULL")
    private Timestamp validUntil;

}
