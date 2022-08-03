package com.inacioferrarini.templates.api.security.models.dtos;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class LoginUserRequestDTO {

    @NotEmpty(message = "Username is required")
    private final String username;

    @NotEmpty(message = "Password is required")
    private final String password;

}
