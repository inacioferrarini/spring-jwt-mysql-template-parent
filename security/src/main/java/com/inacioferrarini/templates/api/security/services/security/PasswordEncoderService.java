package com.inacioferrarini.templates.api.security.services.security;

import org.springframework.security.crypto.password.PasswordEncoder;

public interface PasswordEncoderService {

    PasswordEncoder passwordEncoder();
    String encode(final String password);

}
