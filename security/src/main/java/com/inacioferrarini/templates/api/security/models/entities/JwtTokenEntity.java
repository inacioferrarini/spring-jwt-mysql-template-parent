package com.inacioferrarini.templates.api.security.models.entities;

import com.inacioferrarini.templates.api.base.models.entities.AbstractBaseEntity;
import lombok.Data;

import javax.persistence.*;

@Entity(name = "jwt_token")
@Data
public class JwtTokenEntity extends AbstractBaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity owner;

    private String token;

}
