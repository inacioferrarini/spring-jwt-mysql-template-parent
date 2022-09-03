package com.inacioferrarini.templates.api.sample.services;

import com.inacioferrarini.templates.api.sample.models.entities.BookEntity;
import com.inacioferrarini.templates.api.sample.models.records.BookRecord;
import com.inacioferrarini.templates.api.sample.repositories.BookRepository;
import com.inacioferrarini.templates.api.security.errors.exceptions.InvalidUserCredentialsException;
import com.inacioferrarini.templates.api.security.models.dtos.UserDTO;
import com.inacioferrarini.templates.api.security.models.entities.UserEntity;
import com.inacioferrarini.templates.api.security.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    public BookRecord save(BookRecord book) {
        Optional<UserEntity> userEntity = userRepository.findByUsername(book.owner().getUsername());
        userEntity.orElseThrow(InvalidUserCredentialsException::new);

        BookEntity bookEntity = BookEntity.from(book);
        bookEntity.setOwner(userEntity.get());

        bookRepository.save(bookEntity);

        return BookRecord.from(bookEntity);
    }

    public List<BookRecord> findByOwner(UserDTO owner) {
        Optional<UserEntity> userEntity = userRepository.findByUsername(owner.getUsername());
        userEntity.orElseThrow(InvalidUserCredentialsException::new);

        List<BookRecord> bookList = bookRepository
                .findByOwner(userEntity.get())
                .stream()
                .map(BookRecord::from)
                .toList();

        return bookList;
    }

    public Optional<BookRecord> findByOwnerAndId(UserDTO owner, Long id) {
        Optional<UserEntity> userEntity = userRepository.findByUsername(owner.getUsername());
        userEntity.orElseThrow(InvalidUserCredentialsException::new);

        Optional<BookRecord> book = bookRepository
                .findByOwnerAndId(userEntity.get(), id)
                .stream()
                .map(BookRecord::from)
                .findFirst();

        return book;
    }

    public void delete(UserDTO owner, Long id) {
        Optional<UserEntity> userEntity = userRepository.findByUsername(owner.getUsername());
        userEntity.orElseThrow(InvalidUserCredentialsException::new);

        Optional<BookEntity> bookEntity = bookRepository
                .findByOwnerAndId(userEntity.get(), id);
        if (bookEntity.isEmpty()) {
            return;
        }

        bookRepository.delete(bookEntity.get());
    }

}
