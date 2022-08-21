package com.inacioferrarini.templates.api.sample_feature.controllers;

import com.inacioferrarini.templates.api.sample_feature.models.dtos.BookRecord;
import com.inacioferrarini.templates.api.sample_feature.services.BookService;
import com.inacioferrarini.templates.api.security.models.dtos.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {

    @Autowired
    BookService bookService;

    public UserDTO getRequestAuthor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (UserDTO) authentication.getPrincipal();
    }

    @GetMapping("/api/sample/books")
    public void findAll() {
        BookRecord bookRecord = new BookRecord(
                null,
                getRequestAuthor(),
                "book",
                "teste",
                10.0
        );
        bookService.create(bookRecord);
    }

}
