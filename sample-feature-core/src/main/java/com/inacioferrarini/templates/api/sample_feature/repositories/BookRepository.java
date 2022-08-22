package com.inacioferrarini.templates.api.sample_feature.repositories;

import com.inacioferrarini.templates.api.sample_feature.models.entities.BookEntity;
import com.inacioferrarini.templates.api.security.models.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<BookEntity, Long> {
    List<BookEntity> findByOwner(UserEntity owner);
    Optional<BookEntity> findByOwnerAndId(UserEntity owner, Long id);
}
