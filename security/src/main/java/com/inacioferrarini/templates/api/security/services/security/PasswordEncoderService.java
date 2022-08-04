package com.inacioferrarini.templates.api.security.services.security;

import org.springframework.security.crypto.password.PasswordEncoder;

public interface PasswordEncoderService {

    PasswordEncoder passwordEncoder();
    String encode(final CharSequence password);
    Boolean matches(final CharSequence password, final String encodedPassword);

}
