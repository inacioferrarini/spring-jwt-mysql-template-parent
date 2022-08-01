package com.inacioferrarini.templates.api.security.models.dtos;

import lombok.Data;

@Data
public class RegisterUserRequestDTO {

    private String username;
    private String password;

}
