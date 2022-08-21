package com.inacioferrarini.templates.api.sample_feature.services;

import com.inacioferrarini.templates.api.sample_feature.models.records.BookRecord;

public interface BookService {

    BookRecord create(BookRecord book);

}
