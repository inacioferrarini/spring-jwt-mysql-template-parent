package com.inacioferrarini.templates.api.security.controllers.api;

import com.inacioferrarini.templates.api.security.models.dtos.LoginUserRequestRecord;
import com.inacioferrarini.templates.api.security.models.dtos.LoginUserResponseRecord;
import com.inacioferrarini.templates.api.security.models.dtos.TokenDataRecord;
import com.inacioferrarini.templates.api.security.services.authentication.UserAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class LoginController {

    @Autowired
    UserAuthenticationService authenticationService;

    @PostMapping("/api/security/login")
    ResponseEntity<LoginUserResponseRecord> login(
            @Valid @RequestBody LoginUserRequestRecord loginUserRequestRecord
    ) {
        TokenDataRecord securityToken = authenticationService.login(
                loginUserRequestRecord.username(),
                loginUserRequestRecord.password()
        );

        LoginUserResponseRecord response = new LoginUserResponseRecord(
                securityToken.token(),
                securityToken.validUntil()
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
