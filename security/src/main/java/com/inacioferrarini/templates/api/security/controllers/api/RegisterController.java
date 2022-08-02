package com.inacioferrarini.templates.api.security.controllers.api;

import com.inacioferrarini.templates.api.security.models.User;
import com.inacioferrarini.templates.api.security.models.dtos.RegisterUserRequestDTO;
import com.inacioferrarini.templates.api.security.services.authentication.UserAuthenticationService;
import com.inacioferrarini.templates.api.security.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterController {

    @Autowired
    UserAuthenticationService authenticationService;

    @Autowired
    UserService users;

    @PostMapping("/api/security/register")
    public String register(@RequestBody RegisterUserRequestDTO registerUserRequestDTO) {
        users.save(User.builder()
                       .id(registerUserRequestDTO.getUsername())
                       .username(registerUserRequestDTO.getUsername())
                       .password(registerUserRequestDTO.getPassword())
                       .build()
        );

        return authenticationService
                .login(
                        registerUserRequestDTO.getUsername(),
                        registerUserRequestDTO.getPassword()
                )
                .orElseThrow(
                        () -> new RuntimeException("invalid login and/or password")
                );
    }

}
