package com.inacioferrarini.templates.api;

import com.inacioferrarini.templates.api.security.models.dtos.RegisterUserResponseRecord;
import com.inacioferrarini.templates.api.security.models.entities.SecurityTokenEntity;
import com.inacioferrarini.templates.api.security.models.entities.UserEntity;
import com.inacioferrarini.templates.api.security.repositories.SecurityTokenRepository;
import com.inacioferrarini.templates.api.security.repositories.UserRepository;
import com.inacioferrarini.templates.api.security.services.security.PasswordEncoderService;
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
import java.util.Collections;
import java.util.Date;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
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

    @Test
    public void registerUser_success_mustReturnToken() {
        // Given
        setupFindOneUserSuccess();

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

    private void setupFindOneUserSuccess() {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("Test User");
        userEntity.setPasswordHash(passwordEncoderService.encode("1234"));
        Optional<UserEntity> optionalUserEntity = Optional.of(userEntity);
        when(userRepository.findOne(ArgumentMatchers.any(Example.class))).thenReturn(Optional.of(userEntity));
    }

    private long daysFromNow(final Timestamp timestamp) {
        Timestamp now = new Timestamp(new Date().getTime());
        return Duration.between(now.toInstant(), timestamp.toInstant()).toDays();
    }

}
