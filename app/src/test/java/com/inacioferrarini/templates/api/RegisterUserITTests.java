package com.inacioferrarini.templates.api;

import com.inacioferrarini.templates.api.security.models.dtos.RegisterUserResponseRecord;
import com.inacioferrarini.templates.api.security.models.entities.SecurityTokenEntity;
import com.inacioferrarini.templates.api.security.models.entities.UserEntity;
import com.inacioferrarini.templates.api.security.repositories.SecurityTokenRepository;
import com.inacioferrarini.templates.api.security.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;

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

    @Before
    public void init() {
        //when(userRepository.save(UserEntity.class))



//        Book book = new Book(1L, "Book Name", "Mkyong", new BigDecimal("9.99"));
//        when(mockRepository.findById(1L)).thenReturn(Optional.of(book));
        // configurar repositorio com token que será retornado
    }

    static final String TEST_TOKEN = "";

    @Test
    public void registerUser_success_mustReturnToken() {
        // Given
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
        assertEquals(TEST_TOKEN, response.getBody().token().token());
        assertEquals("", response.getBody().token().validUntil());
        Mockito.verify(userRepository, Mockito.times(0)).save(ArgumentMatchers.any(UserEntity.class));
        Mockito.verify(securityTokenRepository, Mockito.times(0)).save(ArgumentMatchers.any(SecurityTokenEntity.class));
    }

}
