package com.inacioferrarini.templates.api.security.models.dtos;

public record RegisterUserResponseRecord(
        String username,
        String email,
        TokenDataRecord token
) {

}