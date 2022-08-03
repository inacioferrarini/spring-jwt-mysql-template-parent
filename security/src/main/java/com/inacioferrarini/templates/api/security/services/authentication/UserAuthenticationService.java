package com.inacioferrarini.templates.api.security.services.authentication;

import com.inacioferrarini.templates.api.security.models.UserDTO;

import java.util.Optional;

public interface UserAuthenticationService {

    Optional<String> login(
            String username,
            String password
    );
    Optional<UserDTO> findByToken(String token);
    void logout(UserDTO userDTO);

}