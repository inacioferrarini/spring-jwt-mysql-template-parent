package com.inacioferrarini.templates.api.security.controllers.api;

import com.inacioferrarini.templates.api.security.models.dtos.UserDTO;
import com.inacioferrarini.templates.api.security.controllers.api.records.RegisterUserRequestRecord;
import com.inacioferrarini.templates.api.security.controllers.api.records.RegisterUserResponseRecord;
import com.inacioferrarini.templates.api.security.models.records.TokenDataRecord;
import com.inacioferrarini.templates.api.security.services.authentication.UserAuthenticationService;
import com.inacioferrarini.templates.api.security.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<RegisterUserResponseRecord> register(
            @Valid @RequestBody RegisterUserRequestRecord registerUserRequestRecord
    ) {
        final String userName = registerUserRequestRecord.username();
        final String email = registerUserRequestRecord.email();
        final String password = registerUserRequestRecord.password();

        final UserDTO user = new UserDTO(userName, email, password);

        userService.create(user);
        TokenDataRecord securityToken = authenticationService.login(userName, password);

        RegisterUserResponseRecord response = new RegisterUserResponseRecord(
                userName, email, securityToken
        );

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
