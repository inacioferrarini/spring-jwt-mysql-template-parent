package com.inacioferrarini.templates.api.security.models.dtos;

import javax.validation.constraints.NotEmpty;

public record RegisterUserRequestRecord(
        @NotEmpty(message = "{constraints.userName.NotEmpty.message}") String username,
        @NotEmpty(message = "{constraints.email.NotEmpty.message}") String email,
        @NotEmpty(message = "{constraints.password.NotEmpty.message}") String password
) {

}
