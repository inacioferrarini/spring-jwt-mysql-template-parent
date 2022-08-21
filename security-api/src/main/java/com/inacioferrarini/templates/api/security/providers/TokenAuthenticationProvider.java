package com.inacioferrarini.templates.api.security.providers;

import com.inacioferrarini.templates.api.security.services.authentication.UserAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public final class TokenAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    @Autowired
    UserAuthenticationService authenticationService;

    @Override
    protected void additionalAuthenticationChecks(
            final UserDetails d,
            final UsernamePasswordAuthenticationToken auth
    ) {
    }

    @Override
    protected UserDetails retrieveUser(
            final String username,
            final UsernamePasswordAuthenticationToken authentication
    ) {
        final Object token = authentication.getCredentials();
        return Optional.ofNullable(token)
                       .map(String::valueOf)
                       .flatMap(authenticationService::findByToken)
                       .orElseThrow(
                               // TODO: Change exception
                               () -> new UsernameNotFoundException("Couldn't find user: " + token)
                       );
    }

}