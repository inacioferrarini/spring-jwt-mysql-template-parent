package com.inacioferrarini.templates.api.security.models.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity(name = "user")
@Data
public class UserEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String username;

    private String passwordHash;

    @OneToOne(mappedBy = "owner")
    private JwtTokenEntity jwtToken;

}
