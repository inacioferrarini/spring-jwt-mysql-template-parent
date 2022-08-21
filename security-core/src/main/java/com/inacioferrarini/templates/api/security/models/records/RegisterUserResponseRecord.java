package com.inacioferrarini.templates.api.security.models.records;

import com.inacioferrarini.templates.api.security.models.dtos.TokenDataRecord;

public record RegisterUserResponseRecord(
        String username,
        String email,
        TokenDataRecord token
) {

}