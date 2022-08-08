package com.inacioferrarini.templates.api.security.services.authentication;

import com.google.common.collect.ImmutableMap;
import com.inacioferrarini.templates.api.security.errors.exceptions.InvalidUserCredentialsException;
import com.inacioferrarini.templates.api.security.models.UserDTO;
import com.inacioferrarini.templates.api.security.models.dtos.TokenDataRecord;
import com.inacioferrarini.templates.api.security.models.entities.SecurityTokenEntity;
import com.inacioferrarini.templates.api.security.models.entities.UserEntity;
import com.inacioferrarini.templates.api.security.repositories.SecurityTokenRepository;
import com.inacioferrarini.templates.api.security.repositories.UserRepository;
import com.inacioferrarini.templates.api.security.services.security.PasswordEncoderService;
import com.inacioferrarini.templates.api.security.services.token.TokenService;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Optional;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@Service
@AllArgsConstructor(access = PACKAGE)
@FieldDefaults(level = PRIVATE, makeFinal = true)
class TokenAuthenticationServiceImpl implements UserAuthenticationService {

    private static final Logger logger = LoggerFactory.getLogger(TokenAuthenticationServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SecurityTokenRepository securityTokenRepository;

    @Autowired
    private PasswordEncoderService passwordEncoderService;

    @Autowired
    TokenService tokenService;

    @Override
    public TokenDataRecord login(
            final String username,
            final String password
    ) {
        UserEntity searchUserEntity = new UserEntity();
        searchUserEntity.setUsername(username);
        Example<UserEntity> userExample = Example.of(searchUserEntity);

        UserEntity userEntity = userRepository
                .findOne(userExample)
                .filter(user -> {
                    return passwordEncoderService.matches(
                            password, user.getPasswordHash()
                    );
                }).orElseThrow(
                        () -> new InvalidUserCredentialsException()
                );

        TokenDataRecord tokenDataRecord = tokenService.newToken(
                ImmutableMap.of("username", username)
        );

        SecurityTokenEntity tokenEntity = new SecurityTokenEntity();
        tokenEntity.setOwner(userEntity);
        tokenEntity.setToken(tokenDataRecord.token());
        tokenEntity.setValidUntil(tokenDataRecord.validUntil());

        securityTokenRepository.save(tokenEntity);

        return tokenDataRecord;
    }

    @Override
    public Optional<UserDTO> findByToken(final String token) {
//        // TODO: replace by repository usage
//        logger.debug("findByToken: {}", token);
//        return Optional
//                .of(tokenService.verify(token))
//                .map(map -> map.get("username"))
//                .flatMap(userService::findByUsername);
        return null;
    }

    @Override
    public void logout(final UserDTO userDTO) {
    }

}