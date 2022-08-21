package com.inacioferrarini.templates.api.security.controllers.api.records;

import com.inacioferrarini.templates.api.security.models.records.TokenDataRecord;

public record RegisterUserResponseRecord(
        String username,
        String email,
        TokenDataRecord token
) { }