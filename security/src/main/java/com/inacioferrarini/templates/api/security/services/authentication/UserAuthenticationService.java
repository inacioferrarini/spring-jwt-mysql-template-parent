package com.inacioferrarini.templates.api.security.services.authentication;

import com.inacioferrarini.templates.api.security.models.UserDTO;
import com.inacioferrarini.templates.api.security.models.dtos.TokenDataRecord;

import java.util.Optional;

public interface UserAuthenticationService {

    TokenDataRecord login(
            String username,
            String password
    );
    Optional<UserDTO> findByToken(String token);
    void logout(UserDTO userDTO);

}