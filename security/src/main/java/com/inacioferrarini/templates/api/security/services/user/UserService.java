package com.inacioferrarini.templates.api.security.services.user;

import com.inacioferrarini.templates.api.security.models.User;

import java.util.Optional;

public interface UserService {
    User save(User user);
    Optional<User> find(String id);
    Optional<User> findByUsername(String username);
}