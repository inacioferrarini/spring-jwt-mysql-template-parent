package com.inacioferrarini.templates.api.security.services.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordEncoderServiceImpl implements PasswordEncoderService {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public String encode(final CharSequence password) {
        return passwordEncoder().encode(password);
    }

    public Boolean matches(
            final CharSequence password,
            final String encodedPassword
    ) {
        return passwordEncoder().matches(
                password, encodedPassword
        );
    }

}
