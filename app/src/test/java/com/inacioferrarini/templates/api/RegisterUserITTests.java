package com.inacioferrarini.templates.api;

import com.inacioferrarini.templates.api.base.models.dtos.StringErrorResponseRecord;
import com.inacioferrarini.templates.api.base.models.dtos.StringListErrorResponseRecord;
import com.inacioferrarini.templates.api.security.models.dtos.RegisterUserResponseRecord;
import com.inacioferrarini.templates.api.security.models.entities.SecurityTokenEntity;
import com.inacioferrarini.templates.api.security.models.entities.UserEntity;
import com.inacioferrarini.templates.api.security.repositories.SecurityTokenRepository;
import com.inacioferrarini.templates.api.security.repositories.UserRepository;
import com.inacioferrarini.templates.api.security.services.security.PasswordEncoderService;
import com.inacioferrarini.templates.api.tests.mockito.matchers.UserEntityEmailMatcher;
import com.inacioferrarini.templates.api.tests.mockito.matchers.UserEntityUsernameMatcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.domain.Example;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.time.Duration;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class RegisterUserITTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private SecurityTokenRepository securityTokenRepository;

    @Autowired
    private PasswordEncoderService passwordEncoderService;

    @Before
    public void init() {
    }

    @After
    public void resetMocks() {
        Mockito.reset(userRepository);
        Mockito.reset(securityTokenRepository);
    }

    // RegisterUser: Success

    @Test
    public void registerUser_success_mustReturnToken() {
        // Given
        setupUserRepositoryFindOneReturnOneUser();

        final String userInJson = "{\"username\":\"Test User\",\"email\":\"test.user@email.com\",\"password\":\"1234\"}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity<String> entity = new HttpEntity<>(userInJson, headers);

        // When
        ResponseEntity<RegisterUserResponseRecord> response = restTemplate.postForEntity("/api/security/register", entity, RegisterUserResponseRecord.class);

        // Then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Test User", response.getBody().username());
        assertEquals("test.user@email.com", response.getBody().email());
        assertNotNull(response.getBody().token().token());
        assertEquals(2, response.getBody().token().token().chars().filter(ch -> ch == '.').count());
        assertEquals(29l, daysFromNow(response.getBody().token().validUntil()));
        Mockito.verify(userRepository, Mockito.times(1)).save(ArgumentMatchers.any(UserEntity.class));
        Mockito.verify(userRepository, Mockito.times(1)).findOne(ArgumentMatchers.any(Example.class));
        Mockito.verify(securityTokenRepository, Mockito.times(1)).save(ArgumentMatchers.any(SecurityTokenEntity.class));
    }

    // RegisterUser: Failure

    @Test
    public void registerUser_duplicatedUsernameFailure_mustReturnError() {
        // Given
        setupUserRepositoryFindAllReturnOneUserSameUsername();

        final String userInJson = "{\"username\":\"Test User\",\"email\":\"test.user@email.com\",\"password\":\"1234\"}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity<String> entity = new HttpEntity<>(userInJson, headers);

        // When
        ResponseEntity<StringErrorResponseRecord> response = restTemplate.postForEntity("/api/security/register", entity, StringErrorResponseRecord.class);

        // Then
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals(0l, daysFromNow(Timestamp.valueOf(response.getBody().timestamp())));
        assertEquals(HttpStatus.CONFLICT.value(), response.getBody().status());
        assertEquals("Username is already being used.", response.getBody().error());
        Mockito.verify(userRepository, Mockito.times(1)).findAll(ArgumentMatchers.any(Example.class));
        Mockito.verify(userRepository, Mockito.times(0)).save(ArgumentMatchers.any(UserEntity.class));
        Mockito.verify(userRepository, Mockito.times(0)).findOne(ArgumentMatchers.any(Example.class));
        Mockito.verify(securityTokenRepository, Mockito.times(0)).save(ArgumentMatchers.any(SecurityTokenEntity.class));
    }

    @Test
    public void registerUser_duplicatedEmailFailure_mustReturnError() {
        // Given
        setupUserRepositoryFindAllReturnOneUserSameEmail();

        final String userInJson = "{\"username\":\"Test User 2\",\"email\":\"test.user@email.com\",\"password\":\"1234\"}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity<String> entity = new HttpEntity<>(userInJson, headers);

        // When
        ResponseEntity<StringErrorResponseRecord> response = restTemplate.postForEntity("/api/security/register", entity, StringErrorResponseRecord.class);

        // Then
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals(0l, daysFromNow(Timestamp.valueOf(response.getBody().timestamp())));
        assertEquals(HttpStatus.CONFLICT.value(), response.getBody().status());
        assertEquals("Email is already being used.", response.getBody().error());
        Mockito.verify(userRepository, Mockito.times(2)).findAll(ArgumentMatchers.any(Example.class));
        Mockito.verify(userRepository, Mockito.times(0)).save(ArgumentMatchers.any(UserEntity.class));
        Mockito.verify(userRepository, Mockito.times(0)).findOne(ArgumentMatchers.any(Example.class));
        Mockito.verify(securityTokenRepository, Mockito.times(0)).save(ArgumentMatchers.any(SecurityTokenEntity.class));
    }







    private void setupUserRepositoryFindOneReturnOneUser() {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("Test User");
        userEntity.setPasswordHash(passwordEncoderService.encode("1234"));
        Optional<UserEntity> optionalUserEntity = Optional.of(userEntity);
        when(userRepository.findOne(ArgumentMatchers.any(Example.class))).thenReturn(optionalUserEntity);
    }

    private void setupUserRepositoryFindAllReturnOneUserSameUsername() {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("Test User");
        userEntity.setPasswordHash(passwordEncoderService.encode("1234"));
        Example<UserEntity> userExample = Example.of(userEntity);

        List<UserEntity> userEntityList = new ArrayList<UserEntity>();
        userEntityList.add(userEntity);

        when(userRepository.findAll(argThat(new UserEntityUsernameMatcher(userExample)))).thenReturn(userEntityList);
    }

    private void setupUserRepositoryFindAllReturnOneUserSameEmail() {
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail("test.user@email.com");
        userEntity.setPasswordHash(passwordEncoderService.encode("1234"));
        Example<UserEntity> userExample = Example.of(userEntity);

        List<UserEntity> userEntityList = new ArrayList<UserEntity>();
        userEntityList.add(userEntity);

        when(userRepository.findAll(argThat(new UserEntityEmailMatcher(userExample)))).thenReturn(userEntityList);
    }

    private long daysFromNow(final Timestamp timestamp) {
        Timestamp now = new Timestamp(new Date().getTime());
        return Duration.between(now.toInstant(), timestamp.toInstant()).toDays();
    }

}
