package com.inacioferrarini.templates.api.sample_feature.services;

import com.inacioferrarini.templates.api.sample_feature.models.entities.BookEntity;
import com.inacioferrarini.templates.api.sample_feature.models.records.BookRecord;
import com.inacioferrarini.templates.api.sample_feature.repositories.BookRepository;
import com.inacioferrarini.templates.api.security.errors.exceptions.InvalidUserCredentialsException;
import com.inacioferrarini.templates.api.security.models.dtos.UserDTO;
import com.inacioferrarini.templates.api.security.models.entities.UserEntity;
import com.inacioferrarini.templates.api.security.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    public BookRecord create(BookRecord book) {
        Optional<UserEntity> userEntity = userRepository.findByUsername(book.owner().getUsername());
        userEntity.orElseThrow(InvalidUserCredentialsException::new);

        BookEntity bookEntity = BookEntity.from(book);
        bookEntity.setOwner(userEntity.get());

        bookRepository.save(bookEntity);

        return BookRecord.from(bookEntity);
    }

}
