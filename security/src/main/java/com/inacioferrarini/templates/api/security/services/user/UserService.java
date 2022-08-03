package com.inacioferrarini.templates.api.security.services.user;

import com.inacioferrarini.templates.api.security.models.UserDTO;
import com.inacioferrarini.templates.api.security.models.entities.UserEntity;

import java.util.Optional;

public interface UserService {
    void create(UserDTO user);
    Optional<UserDTO> findById(String id);
    Optional<UserDTO> findByUsername(String username);
    Optional<UserDTO> findByEmail(String email);
}