package com.inacioferrarini.templates.api.security.repositories;

import com.inacioferrarini.templates.api.security.models.entities.SecurityTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SecurityTokenRepository extends JpaRepository<SecurityTokenEntity, Long> { }
