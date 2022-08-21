package com.inacioferrarini.templates.api.base.models.records;

import java.time.LocalDateTime;

public record StringErrorResponseRecord(
        LocalDateTime timestamp,
        int status,
        String error
) { }
