package com.inacioferrarini.templates.api.sample_feature.models.records;

import com.inacioferrarini.templates.api.sample_feature.models.entities.BookEntity;
import com.inacioferrarini.templates.api.security.models.dtos.UserDTO;

public record BookRecord(
        Long id,
        UserDTO owner,
        String name,
        String author,
        Double price
) {

    public static BookRecord from(BookEntity bookEntity) {
        return new BookRecord(
                bookEntity.getId(),
                UserDTO.from(bookEntity.getOwner()),
                bookEntity.getName(),
                bookEntity.getAuthor(),
                bookEntity.getPrice().doubleValue()
        );
    }

}
