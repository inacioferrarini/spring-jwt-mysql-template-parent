package com.inacioferrarini.templates.api.security.repositories;

import com.inacioferrarini.templates.api.security.models.entities.JwtTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JWTTokenRepository extends JpaRepository<JwtTokenEntity, Long> { }
