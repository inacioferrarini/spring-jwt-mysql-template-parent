package com.inacioferrarini.templates.api.security.services.authentication;

import com.inacioferrarini.templates.api.security.models.dtos.UserDTO;
import com.inacioferrarini.templates.api.security.models.records.TokenDataRecord;

import java.util.Optional;

public interface UserAuthenticationService {

    TokenDataRecord login(
            String username,
            String password
    );
    Optional<UserDTO> findByToken(String token);
    void logout(UserDTO userDTO);

}