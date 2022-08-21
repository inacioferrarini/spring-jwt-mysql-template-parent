package com.inacioferrarini.templates.api.sample_feature.models.dtos;

import com.inacioferrarini.templates.api.security.models.dtos.UserDTO;

public record BookRecord(
        Long id,
        UserDTO owner,
        String name,
        String author,
        Double price
) {

}
