package com.inacioferrarini.templates.api.security.models.records;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public record LoginUserResponseRecord(
        String token,
        @JsonProperty("valid_until") LocalDateTime timestamp
) {

}
