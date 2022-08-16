package com.inacioferrarini.templates.api.security.models.dtos;

import javax.validation.constraints.NotEmpty;

public record LoginUserRequestRecord(
        @NotEmpty(message = "{constraints.userName.NotEmpty.message}") String username,
        @NotEmpty(message = "{constraints.password.NotEmpty.message}") String password
) {

}
