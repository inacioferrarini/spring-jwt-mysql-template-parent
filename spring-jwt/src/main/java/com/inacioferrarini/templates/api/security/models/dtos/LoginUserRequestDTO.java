package com.inacioferrarini.templates.api.security.models.dtos;

import lombok.Data;

@Data
public class LoginUserRequestDTO {

    private String username;
    private String password;

}
