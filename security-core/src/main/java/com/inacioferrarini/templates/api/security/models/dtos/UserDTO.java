package com.inacioferrarini.templates.api.security.models.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.inacioferrarini.templates.api.security.models.entities.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.util.ArrayList;
import java.util.Collection;

public class UserDTO implements UserDetails {

    @Serial
    private static final long serialVersionUID = 2396654715019746670L;

    private final String username;
    private final String email;
    private final String password;
    private final ArrayList<GrantedAuthority> authorities;
    private final Boolean accountNonExpired;
    private final Boolean accountNonLocked;
    private final Boolean credentialsNonExpired;
    private final Boolean enabled;

    public static UserDTO from(UserEntity userEntity) {
        return new UserDTO(
                userEntity.getUsername(),
                userEntity.getEmail(),
                null,
                new ArrayList<>(),
                userEntity.getNonExpired(),
                userEntity.getNonLocked(),
                userEntity.getCredentialsNotExpired(),
                userEntity.getEnabled()
        );
    }

    public UserDTO(
            String username,
            String email,
            String password
    ) {
        this(
          username,
          email,
          password,
          new ArrayList<>(),
          true,
          true,
          true,
          true
        );
    }

    public UserDTO(
            String username,
            String email,
            String password,
            ArrayList<GrantedAuthority> authorities,
            Boolean accountNonExpired,
            Boolean accountNonLocked,
            Boolean credentialsNonExpired,
            Boolean enabled
    ) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
        this.accountNonExpired = accountNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.credentialsNonExpired = credentialsNonExpired;
        this.enabled = enabled;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    public String getEmail() {
        return this.email;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return this.password;
    }

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

}