package com.inacioferrarini.templates.api.security.tests;

import com.google.common.collect.ImmutableMap;
import com.inacioferrarini.templates.api.security.models.entities.SecurityTokenEntity;
import com.inacioferrarini.templates.api.security.models.entities.UserEntity;
import com.inacioferrarini.templates.api.security.models.records.TokenDataRecord;
import com.inacioferrarini.templates.api.security.repositories.SecurityTokenRepository;
import com.inacioferrarini.templates.api.security.repositories.UserRepository;
import com.inacioferrarini.templates.api.security.services.authentication.UserAuthenticationService;
import com.inacioferrarini.templates.api.security.services.security.PasswordEncoderService;
import com.inacioferrarini.templates.api.security.services.token.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.HashSet;

@Component
public class SecurityTestsHelper {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SecurityTokenRepository securityTokenRepository;

    @Autowired
    private PasswordEncoderService passwordEncoderService;

    @Autowired
    UserAuthenticationService authenticationService;

    public UserEntity createTestUser() {
        final String username = "Test User";
        final String email = "test.user@email.com";
        final String password = "1234";

        final String encodedPassword = passwordEncoderService.encode(password);
        final UserEntity userEntity = new UserEntity(
                username,
                email,
                encodedPassword,
                new HashSet<>(),
                true,
                true,
                true,
                true
        );
        userRepository.save(userEntity);

        return userEntity;
    }

    public UserEntity create2ndTestUser() {
        final String username = "2nd Test User";
        final String email = "test.user.2nd@email.com";
        final String password = "1234";

        final String encodedPassword = passwordEncoderService.encode(password);
        final UserEntity userEntity = new UserEntity(
                username,
                email,
                encodedPassword,
                new HashSet<>(),
                true,
                true,
                true,
                true
        );
        userRepository.save(userEntity);

        return userEntity;
    }

    public long countUsers() {
        return userRepository.count();
    }

    public TokenDataRecord createSecurityToken() {
        final String username = "Test User";
        final String password = "1234";

        TokenDataRecord securityToken = authenticationService.login(
                username,
                password
        );

        return securityToken;
    }

    public long countSecurityTokens() {
        return securityTokenRepository.count();
    }

    public void deleteAll() {
        securityTokenRepository.deleteAll();
        userRepository.deleteAll();
    }

}
