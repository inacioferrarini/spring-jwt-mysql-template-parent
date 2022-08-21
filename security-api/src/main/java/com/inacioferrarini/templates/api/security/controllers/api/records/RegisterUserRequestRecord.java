package com.inacioferrarini.templates.api.security.controllers.api.records;

import javax.validation.constraints.NotEmpty;

public record RegisterUserRequestRecord(
        @NotEmpty(message = "{constraints.user_name.not_empty.message}") String username,
        @NotEmpty(message = "{constraints.email.not_empty.message}") String email,
        @NotEmpty(message = "{constraints.password.not_empty.message}") String password
) { }
