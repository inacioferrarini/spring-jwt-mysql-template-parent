package com.inacioferrarini.templates.api.security.models.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public record LoginUserResponseRecord(
        String token,
        @JsonProperty("valid_until") LocalDateTime timestamp
) {

}
