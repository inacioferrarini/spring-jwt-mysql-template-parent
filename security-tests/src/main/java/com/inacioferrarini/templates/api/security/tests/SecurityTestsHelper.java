package com.inacioferrarini.templates.api.security.tests;

import com.inacioferrarini.templates.api.security.models.entities.UserEntity;
import com.inacioferrarini.templates.api.security.repositories.SecurityTokenRepository;
import com.inacioferrarini.templates.api.security.repositories.UserRepository;
import com.inacioferrarini.templates.api.security.services.security.PasswordEncoderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
public class SecurityTestsHelper {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SecurityTokenRepository securityTokenRepository;

    @Autowired
    private PasswordEncoderService passwordEncoderService;

    public void createTestUser() {
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
    }

    public long countUsers() {
        return userRepository.count();
    }

    public long countSecurityTokens() {
        return securityTokenRepository.count();
    }

    public void deleteAll() {
        securityTokenRepository.deleteAll();
        userRepository.deleteAll();
    }

}
