package com.inacioferrarini.templates.api.security.filters;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.google.common.net.HttpHeaders.AUTHORIZATION;
import static java.util.Optional.ofNullable;

public final class TokenAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private static final Logger logger = LoggerFactory.getLogger(TokenAuthenticationFilter.class);

    public TokenAuthenticationFilter(final RequestMatcher requiresAuth) {
        super(requiresAuth);
    }

    @Override
    public Authentication attemptAuthentication(
            final HttpServletRequest request,
            final HttpServletResponse response
    ) {
        final String param = request.getHeader(AUTHORIZATION);
        final String token = ofNullable(param)
                .map(value -> StringUtils.removeStart(
                             value,
                             "Bearer"
                     )
                )
                .map(String::trim)
                .orElseThrow(
                        () -> new BadCredentialsException("No Token Found!") // TODO: Handle this
                );

        final Authentication auth = new UsernamePasswordAuthenticationToken(
                token,
                token
        );

        logger.debug("auth: {}", auth);

        return getAuthenticationManager().authenticate(auth);
    }

    @Override
    protected void successfulAuthentication(
            final HttpServletRequest request,
            final HttpServletResponse response,
            final FilterChain chain,
            final Authentication authResult
    ) throws IOException, ServletException {
        super.successfulAuthentication(
                request,
                response,
                chain,
                authResult
        );
        chain.doFilter(
                request,
                response
        );
    }

}
