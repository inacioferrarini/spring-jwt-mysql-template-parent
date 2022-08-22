package com.inacioferrarini.templates.api.security.models.entities;

import com.inacioferrarini.templates.api.base.models.entities.AbstractBaseEntity;
import com.inacioferrarini.templates.api.security.models.dtos.UserDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "users")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class UserEntity extends AbstractBaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String username;

    @Column(unique = true)
    private String email;

    @Column
    private String passwordHash;

    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<SecurityTokenEntity> jwtTokens;

    @Column
    private Boolean nonExpired;

    @Column
    private Boolean nonLocked;

    @Column
    private Boolean credentialsNotExpired;

    @Column
    private Boolean enabled;

    public UserEntity(
            String username,
            String email,
            String passwordHash,
            Set<SecurityTokenEntity> jwtTokens,
            Boolean nonExpired,
            Boolean nonLocked,
            Boolean credentialsNotExpired,
            Boolean enabled
    ) {
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
        this.jwtTokens = jwtTokens;
        this.nonExpired = nonExpired;
        this.nonLocked = nonLocked;
        this.credentialsNotExpired = credentialsNotExpired;
        this.enabled = enabled;
    }

    public static UserEntity from(UserDTO user) {
        return new UserEntity(
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                new HashSet<SecurityTokenEntity>(),
                user.isAccountNonExpired(),
                user.isAccountNonLocked(),
                user.isCredentialsNonExpired(),
                user.isEnabled()
        );
    }

}
