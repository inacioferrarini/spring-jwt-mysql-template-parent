package com.inacioferrarini.templates.api.security.models.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public record TokenDataRecord(
        String token,
        @JsonProperty("valid_until") LocalDateTime validUntil
) {

}
