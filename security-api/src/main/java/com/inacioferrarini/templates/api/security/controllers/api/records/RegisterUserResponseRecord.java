package com.inacioferrarini.templates.api.security.controllers.api.records;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.inacioferrarini.templates.api.security.models.records.TokenDataRecord;

@JsonPropertyOrder({ "username", "email", "token" })
public record RegisterUserResponseRecord(
        String username,
        String email,
        TokenDataRecord token
) { }