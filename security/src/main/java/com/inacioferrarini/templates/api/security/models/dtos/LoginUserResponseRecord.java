package com.inacioferrarini.templates.api.security.models.dtos;

import java.sql.Timestamp;

public record LoginUserResponseRecord(
        String token,
        Timestamp validUntil
) {

}
