package com.inacioferrarini.templates.api.security.models.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "user")
@Data
@NoArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique=true)
    private String username;

    @Column(unique=true)
    private String email;

    @Column
    private String passwordHash;

    @OneToOne(mappedBy = "owner", optional = true)
    private JwtTokenEntity jwtToken;

    public UserEntity(String username, String email, String passwordHash) {
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
    }

}
