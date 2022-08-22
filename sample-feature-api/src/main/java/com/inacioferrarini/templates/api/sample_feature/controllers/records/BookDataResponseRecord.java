package com.inacioferrarini.templates.api.sample_feature.controllers.records;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.inacioferrarini.templates.api.sample_feature.models.records.BookRecord;

@JsonPropertyOrder({ "id", "name", "author", "price" })
public record BookDataResponseRecord(
        Long id,
        String name,
        String author,
        Double price
) {

    public static BookDataResponseRecord from(BookRecord book) {
        return new BookDataResponseRecord(
                book.id(),
                book.name(),
                book.author(),
                book.price()
        );
    }

}
