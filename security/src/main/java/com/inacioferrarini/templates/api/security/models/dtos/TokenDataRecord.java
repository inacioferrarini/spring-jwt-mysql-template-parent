package com.inacioferrarini.templates.api.security.models.dtos;

import java.sql.Timestamp;

public record TokenDataRecord(
        String token,
        Timestamp validUntil
) {

}
