package com.inacioferrarini.templates.api.security.models.dtos;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class RegisterUserRequestDTO {

    @NotEmpty(message = "{constraints.userName.NotEmpty.message}")
    private final String username;

    @NotEmpty(message = "{constraints.email.NotEmpty.message}")
    private final String email;

    @NotEmpty(message = "{constraints.password.NotEmpty.message}")
    private final String password;

}
