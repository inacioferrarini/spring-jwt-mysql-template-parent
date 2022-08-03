package com.inacioferrarini.templates.api.security.models.entities;

import lombok.Data;

import javax.persistence.*;

@Entity(name = "jwt_token")
@Data
public class JwtTokenEntity {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity owner;

    private String token;

}
