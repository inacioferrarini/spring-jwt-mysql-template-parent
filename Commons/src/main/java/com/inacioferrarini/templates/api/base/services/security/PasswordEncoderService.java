package com.inacioferrarini.templates.api.base.services.security;

import org.springframework.security.crypto.password.PasswordEncoder;

public interface PasswordEncoderService {

    PasswordEncoder passwordEncoder();

}
