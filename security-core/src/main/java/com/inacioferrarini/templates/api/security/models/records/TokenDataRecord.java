package com.inacioferrarini.templates.api.security.models.records;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.time.LocalDateTime;

@JsonPropertyOrder({ "token", "valid_until" })
public record TokenDataRecord(
        String token,
        @JsonProperty("valid_until") LocalDateTime validUntil
) { }
