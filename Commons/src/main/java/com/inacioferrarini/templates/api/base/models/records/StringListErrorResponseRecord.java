package com.inacioferrarini.templates.api.base.models.records;

import java.time.LocalDateTime;
import java.util.List;

public record StringListErrorResponseRecord(
        LocalDateTime timestamp,
        int status,
        List<String> errors
) { }