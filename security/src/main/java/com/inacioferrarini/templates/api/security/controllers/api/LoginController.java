package com.inacioferrarini.templates.api.security.controllers.api;

import com.inacioferrarini.templates.api.security.models.dtos.LoginUserRequestDTO;
import com.inacioferrarini.templates.api.security.services.authentication.UserAuthenticationService;
import com.inacioferrarini.templates.api.security.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    UserAuthenticationService authenticationService;

    @Autowired
    UserService users;

    @PostMapping("/api/security/login")
    String login(
            @RequestBody LoginUserRequestDTO loginUserRequestDTO
    ) {
        return authenticationService
                .login(
                        loginUserRequestDTO.getUsername(),
                        loginUserRequestDTO.getPassword()
                )
                .orElseThrow(
                        () -> new RuntimeException("invalid login and/or password")
                );
    }

}
