package com.inacioferrarini.templates.api.security.services.user;

import com.inacioferrarini.templates.api.security.models.dtos.UserDTO;

import java.util.Optional;

public interface UserService {

    void create(UserDTO user);
    Optional<UserDTO> findByUsername(String username);
    Optional<UserDTO> findByEmail(String email);

}