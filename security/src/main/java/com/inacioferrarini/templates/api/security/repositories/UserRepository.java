package com.inacioferrarini.templates.api.security.repositories;

import com.inacioferrarini.templates.api.security.models.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> { }
