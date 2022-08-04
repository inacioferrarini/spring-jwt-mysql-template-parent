package com.inacioferrarini.templates.api.security.controllers.api;

import com.inacioferrarini.templates.api.security.models.UserDTO;
import com.inacioferrarini.templates.api.security.models.dtos.RegisterUserRequestDTO;
import com.inacioferrarini.templates.api.security.services.authentication.UserAuthenticationService;
import com.inacioferrarini.templates.api.security.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class RegisterController {

    @Autowired
    UserAuthenticationService authenticationService;

    @Autowired
    UserService userService;

    @PostMapping("/api/security/register")
    public String register(@Valid @RequestBody RegisterUserRequestDTO registerUserRequestDTO) {
        final String userName = registerUserRequestDTO.getUsername();
        final String email = registerUserRequestDTO.getEmail();
        final String password = registerUserRequestDTO.getPassword();

        final UserDTO user = new UserDTO(userName, email, password);

        userService.create(user);

        return authenticationService
                .login(userName, password)
                .orElseThrow(
                        // TODO: Throw Exception - Handle this exception inside the service
                        () -> new RuntimeException("invalid login and/or password")
                );
    }

}
