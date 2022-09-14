package com.inacioferrarini.templates.api.sample.tests;

import com.inacioferrarini.templates.api.sample.models.entities.BookEntity;
import com.inacioferrarini.templates.api.sample.repositories.BookRepository;
import com.inacioferrarini.templates.api.security.models.entities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class SampleTestsHelper {

    @Autowired
    private BookRepository bookRepository;

    public BookEntity createTestBook(UserEntity owner) {
        final String name = "Test Book";
        final String author = "Test Book Author";
        final BigDecimal price = new BigDecimal(10.50);

        final BookEntity bookEntity = new BookEntity();
        bookEntity.setOwner(owner);
        bookEntity.setName(name);
        bookEntity.setAuthor(author);
        bookEntity.setPrice(price);

        bookRepository.save(bookEntity);

        return bookEntity;
    }

    public List<BookEntity> allBooks() {
        return bookRepository.findAll();
    }

    public long countBooks() {
        return bookRepository.count();
    }

    public void deleteAll() {
        bookRepository.deleteAll();
    }

}
