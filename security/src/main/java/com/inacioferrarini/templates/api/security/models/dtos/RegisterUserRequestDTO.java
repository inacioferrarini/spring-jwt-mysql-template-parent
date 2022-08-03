package com.inacioferrarini.templates.api.security.models.dtos;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class RegisterUserRequestDTO {

    @NotEmpty(message = "Username is required")
    private final String username;

    @NotEmpty(message = "Email is required")
    private final String email;

    @NotEmpty(message = "Password is required")
    private final String password;

}
