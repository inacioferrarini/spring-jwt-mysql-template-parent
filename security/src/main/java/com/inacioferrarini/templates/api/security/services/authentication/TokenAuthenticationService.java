package com.inacioferrarini.templates.api.security.services.authentication;

import com.google.common.collect.ImmutableMap;
import com.inacioferrarini.templates.api.security.models.User;
import com.inacioferrarini.templates.api.security.services.token.TokenService;
import com.inacioferrarini.templates.api.security.services.user.UserService;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@Service
@AllArgsConstructor(access = PACKAGE)
@FieldDefaults(level = PRIVATE, makeFinal = true)
final class TokenAuthenticationService implements UserAuthenticationService {

    private static final Logger logger = LoggerFactory.getLogger(TokenAuthenticationService.class);

    @Autowired
    TokenService tokenService;
    @Autowired
    UserService users;

    @Override
    public Optional<String> login(
            final String username,
            final String password
    ) {
        return users
                .findByUsername(username)
                .filter(user -> Objects.equals(
                        password,
                        user.getPassword()
                ))
                .map(user -> tokenService.newToken(ImmutableMap.of(
                        "username",
                        username
                )));
    }

    @Override
    public Optional<User> findByToken(final String token) {
        logger.debug("findByToken: {}", token);
        return Optional
                .of(tokenService.verify(token))
                .map(map -> map.get("username"))
                .flatMap(users::findByUsername);
    }

    @Override
    public void logout(final User user) {
    }

}