package com.inacioferrarini.templates.api.security.controllers.api;

import com.inacioferrarini.templates.api.security.services.authentication.UserAuthenticationService;
import com.inacioferrarini.templates.api.security.models.User;
import com.inacioferrarini.templates.api.security.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class RegisterController {

    @Autowired
    UserAuthenticationService authentication;

    @Autowired
    UserService users;

    @PostMapping("/api/security/register")
    public String register(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        String password = body.get("password");
        users.save(User.builder()
                       .id(username)
                       .username(username)
                       .password(password)
                       .build()
        );

        return authentication.login(
                                     username,
                                     password
                             )
                             .orElseThrow(
                                     () -> new RuntimeException("invalid login and/or password")
                             );
    }

}
