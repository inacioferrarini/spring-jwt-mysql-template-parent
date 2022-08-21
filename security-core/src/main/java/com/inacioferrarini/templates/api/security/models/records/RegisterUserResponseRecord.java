package com.inacioferrarini.templates.api.security.models.records;

public record RegisterUserResponseRecord(
        String username,
        String email,
        TokenDataRecord token
) { }