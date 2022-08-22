package com.inacioferrarini.templates.api.sample_feature.controllers;

import com.inacioferrarini.templates.api.errors.exceptions.ResourceNotFoundException;
import com.inacioferrarini.templates.api.sample_feature.controllers.records.BookDataRequestRecord;
import com.inacioferrarini.templates.api.sample_feature.controllers.records.BookDataResponseRecord;
import com.inacioferrarini.templates.api.sample_feature.models.records.BookRecord;
import com.inacioferrarini.templates.api.sample_feature.services.BookService;
import com.inacioferrarini.templates.api.security.components.AuthenticatedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class BookController {

    @Autowired
    BookService bookService;

    @Autowired
    AuthenticatedUser authenticatedUser;

//    @GetMapping("/api/sample/books")
//    public void findAll() {
//        // TODO: Add requireNonNull
//
//        BookRecord bookRecord = new BookRecord(
//                null,
//                authenticatedUser.getRequestAuthor(),
//                "book",
//                "teste",
//                10.0
//        );
//        bookService.create(bookRecord);
//    }

    @GetMapping("/api/sample/books")
    ResponseEntity<List<BookDataResponseRecord>> findAll() {
        List<BookRecord> bookList = bookService
                .findByOwner(authenticatedUser.getRequestAuthor());
        List<BookDataResponseRecord> response = bookList
                .stream()
                .map(BookDataResponseRecord::from)
                .toList();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/api/sample/books/{id}")
    ResponseEntity<BookDataResponseRecord> findOne(@PathVariable Long id) {
        Optional<BookRecord> book = bookService
                .findByOwnerAndId(authenticatedUser.getRequestAuthor(), id);
        book.orElseThrow(ResourceNotFoundException::new);

        BookDataResponseRecord response = BookDataResponseRecord.from(book.get());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/api/sample/books")
    ResponseEntity<BookDataResponseRecord> create(
            @Valid @RequestBody BookDataRequestRecord bookDataRequestRecord
    ) {
        BookRecord bookRecord = new BookRecord(
                null,
                authenticatedUser.getRequestAuthor(),
                bookDataRequestRecord.name(),
                bookDataRequestRecord.author(),
                bookDataRequestRecord.price()
        );
        BookRecord createdBook = bookService.create(bookRecord);
        BookDataResponseRecord response = BookDataResponseRecord.from(createdBook);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

//    // Update
//    // Save or update
//    @PutMapping("/books/{id}")
//    Book saveOrUpdate(@RequestBody Book newBook, @PathVariable Long id) {
//
//        return repository.findById(id)
//                         .map(x -> {
//                             x.setName(newBook.getName());
//                             x.setAuthor(newBook.getAuthor());
//                             x.setPrice(newBook.getPrice());
//                             return repository.save(x);
//                         })
//                         .orElseGet(() -> {
//                             newBook.setId(id);
//                             return repository.save(newBook);
//                         });
//    }

    // Delete
//    @DeleteMapping("/books/{id}")
//    void deleteBook(@PathVariable Long id) {
//        repository.deleteById(id);
//    }


}
