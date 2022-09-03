package com.inacioferrarini.templates.api.sample.services;

import com.inacioferrarini.templates.api.sample.models.records.BookRecord;
import com.inacioferrarini.templates.api.security.models.dtos.UserDTO;

import java.util.List;
import java.util.Optional;

public interface BookService {

    BookRecord save(BookRecord book);
    List<BookRecord> findByOwner(UserDTO owner);
    Optional<BookRecord> findByOwnerAndId(UserDTO owner, Long id);
    void delete(UserDTO owner, Long id);

}
