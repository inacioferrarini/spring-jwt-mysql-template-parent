package com.inacioferrarini.templates.api.security.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class UserDTO implements UserDetails {

    private static final long serialVersionUID = 2396654715019746670L;

    private final String username;
    private final String email;
    private final String password;

    public UserDTO(
            String username,
            String email,
            String password
    ) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    @JsonIgnore
    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return new ArrayList<>();
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return password;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    public String getEmail() {
        return email;
    }

}