package com.inacioferrarini.templates.api.security.services.user;

import com.inacioferrarini.templates.api.security.models.UserDTO;
import com.inacioferrarini.templates.api.security.models.entities.UserEntity;
import com.inacioferrarini.templates.api.security.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
final class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void create(UserDTO user) {
        final UserEntity userEntity = new UserEntity(
                user.getUsername(),
                user.getEmail(),
                user.getPassword()
        );
        userRepository.save(userEntity);
    }

    public Optional<UserDTO> findById(String id) {
        UserEntity searchUserEntity = new UserEntity();
        searchUserEntity.setUsername(id);
        Example<UserEntity> userExample = Example.of(searchUserEntity);

        return userRepository
                .findAll(userExample)
                .stream()
                .map(userEntity -> new UserDTO(
                        userEntity.getUsername(),
                        userEntity.getUsername(),
                        userEntity.getEmail(),
                        userEntity.getPasswordHash()
                ))
                .findFirst();
    }

    public Optional<UserDTO> findByUsername(String username) {
        UserEntity searchUserEntity = new UserEntity();
        searchUserEntity.setUsername(username);
        Example<UserEntity> userExample = Example.of(searchUserEntity);

        return userRepository
                .findAll(userExample)
                .stream()
                .map(userEntity -> new UserDTO(
                        userEntity.getUsername(),
                        userEntity.getUsername(),
                        userEntity.getEmail(),
                        userEntity.getPasswordHash()
                ))
                .findFirst();
    }

    public Optional<UserDTO> findByEmail(String email) {
        UserEntity searchUserEntity = new UserEntity();
        searchUserEntity.setEmail(email);
        Example<UserEntity> userExample = Example.of(searchUserEntity);

        return userRepository
                .findAll(userExample)
                .stream()
                .map(userEntity -> new UserDTO(
                        userEntity.getUsername(),
                        userEntity.getUsername(),
                        userEntity.getEmail(),
                        userEntity.getPasswordHash()
                ))
                .findFirst();
    }

}

