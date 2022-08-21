package com.inacioferrarini.templates.api.sample_feature.services;

import com.inacioferrarini.templates.api.sample_feature.models.dtos.BookRecord;
import com.inacioferrarini.templates.api.security.models.dtos.UserDTO;

import java.util.Optional;

public interface BookService {

    void create(BookRecord book);
//    Optional<UserDTO> findById(String id);


}
