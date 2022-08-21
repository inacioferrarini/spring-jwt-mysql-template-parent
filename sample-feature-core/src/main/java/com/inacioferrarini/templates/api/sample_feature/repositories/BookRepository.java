package com.inacioferrarini.templates.api.sample_feature.repositories;

import com.inacioferrarini.templates.api.sample_feature.models.entities.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<BookEntity, Long> { }
