package com.inacioferrarini.templates.api.security.services.authentication;

import com.inacioferrarini.templates.api.security.models.User;

import java.util.Optional;

public interface UserAuthenticationService {

    Optional<String> login(
            String username,
            String password
    );
    Optional<User> findByToken(String token);
    void logout(User user);

}