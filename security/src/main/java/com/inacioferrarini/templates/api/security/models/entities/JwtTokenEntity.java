package com.inacioferrarini.templates.api.security.models.entities;

import lombok.Data;

import javax.persistence.*;

@Entity(name = "jwt_token")
@Data
public class JwtTokenEntity {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity owner;

    private String token;

}
