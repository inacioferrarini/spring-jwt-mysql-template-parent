package com.inacioferrarini.templates.api.sample.controllers.records;

import javax.validation.constraints.NotNull;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;

public record BookDataRequestRecord(
        @NotEmpty(message = "{constraints.name.not_empty.message}") String name,
        @NotEmpty(message = "{constraints.author.not_empty.message}") String author,
        @NotNull(message = "{constraints.price.not_empty.message}")
        @DecimalMin(message = "{constraints.price.minValue.message}", value = "0.01") Double price
) { }
