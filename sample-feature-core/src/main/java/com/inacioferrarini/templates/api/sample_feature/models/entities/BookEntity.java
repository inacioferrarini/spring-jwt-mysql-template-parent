package com.inacioferrarini.templates.api.sample_feature.models.entities;

import com.inacioferrarini.templates.api.base.models.entities.AbstractBaseEntity;
import com.inacioferrarini.templates.api.sample_feature.models.records.BookRecord;
import com.inacioferrarini.templates.api.security.models.entities.UserEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity(name = "book")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class BookEntity extends AbstractBaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity owner;

    private String name;

    private String author;

    private BigDecimal price;

    public static BookEntity from(BookRecord book) {
        BookEntity bookEntity = new BookEntity();
        bookEntity.id = book.id();
        bookEntity.owner = null;
        bookEntity.name = book.name();
        bookEntity.author = book.author();
        bookEntity.price = new BigDecimal(book.price());
        return bookEntity;
    }

}
