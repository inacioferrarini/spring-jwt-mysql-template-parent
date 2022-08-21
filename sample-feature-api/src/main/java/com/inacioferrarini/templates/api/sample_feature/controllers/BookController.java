package com.inacioferrarini.templates.api.sample_feature.controllers;

import com.inacioferrarini.templates.api.sample_feature.controllers.records.CreateBookRequestRecord;
import com.inacioferrarini.templates.api.sample_feature.controllers.records.CreateBookResponseRecord;
import com.inacioferrarini.templates.api.sample_feature.models.records.BookRecord;
import com.inacioferrarini.templates.api.sample_feature.services.BookService;
import com.inacioferrarini.templates.api.security.components.AuthenticatedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

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

    // FindAll
//    @GetMapping("/books")
//    List<Book> findAll() {
//        return repository.findAll();
//    }

    @PostMapping("/api/sample/books")
    ResponseEntity<CreateBookResponseRecord> create(
            @Valid @RequestBody CreateBookRequestRecord createBookRequestRecord
    ) {
        BookRecord bookRecord = new BookRecord(
                null,
                authenticatedUser.getRequestAuthor(),
                createBookRequestRecord.name(),
                createBookRequestRecord.author(),
                createBookRequestRecord.price()
        );
        bookService.create(bookRecord);

        CreateBookResponseRecord response = new CreateBookResponseRecord();

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
