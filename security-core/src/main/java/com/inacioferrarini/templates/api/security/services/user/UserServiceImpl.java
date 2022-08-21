package com.inacioferrarini.templates.api.security.services.user;

import com.inacioferrarini.templates.api.security.errors.exceptions.FieldValueAlreadyInUseException;
import com.inacioferrarini.templates.api.security.models.dtos.UserDTO;
import com.inacioferrarini.templates.api.security.models.entities.UserEntity;
import com.inacioferrarini.templates.api.security.repositories.UserRepository;
import com.inacioferrarini.templates.api.security.services.security.PasswordEncoderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
final class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoderService passwordEncoderService;

    @Override
    public void create(UserDTO user) {
        findByUsername(user.getUsername())
                .ifPresent(userEntity -> {
                    throw new FieldValueAlreadyInUseException(
                            FieldValueAlreadyInUseException.Field.USERNAME
                    );
                });
        findByEmail(user.getEmail())
                .ifPresent(userEntity -> {
                    throw new FieldValueAlreadyInUseException(
                            FieldValueAlreadyInUseException.Field.EMAIL
                    );
                });

        final String encodedPassword = passwordEncoderService.encode(user.getPassword());
        final UserEntity userEntity = UserEntity.from(user);
        userEntity.setPasswordHash(encodedPassword);

        userRepository.save(userEntity);
    }

    public Optional<UserDTO> findByUsername(String username) {
        return userRepository.
                findByUsername(username)
                .map(UserDTO::from);
    }

    public Optional<UserDTO> findByEmail(String email) {
        return userRepository.
                findByEmail(email)
                .map(UserDTO::from);
    }

}

