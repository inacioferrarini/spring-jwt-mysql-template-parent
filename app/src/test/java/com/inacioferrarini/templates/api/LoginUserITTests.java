package com.inacioferrarini.templates.api;

import com.inacioferrarini.templates.api.base.models.dtos.StringErrorResponseRecord;
import com.inacioferrarini.templates.api.base.models.dtos.StringListErrorResponseRecord;
import com.inacioferrarini.templates.api.security.models.dtos.LoginUserResponseRecord;
import com.inacioferrarini.templates.api.security.models.entities.SecurityTokenEntity;
import com.inacioferrarini.templates.api.security.tests.SecurityTestsHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class LoginUserITTests {

    private static final String API_URL = "/api/security/login";

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private SecurityTestsHelper securityTestsHelper;

    // ---------------------------------------------------------------------------------
    // Login: Success
    // ---------------------------------------------------------------------------------
    @Test
    public void login_success_mustReturnToken() {
        // Given
        securityTestsHelper.deleteAll();
        securityTestsHelper.createTestUser();

        final String requestBody = "{\"username\":\"Test User\",\"password\":\"1234\"}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        // When
        ResponseEntity<LoginUserResponseRecord> response = restTemplate.postForEntity(API_URL, entity, LoginUserResponseRecord.class);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody().token());
        assertEquals(2, response.getBody().token().chars().filter(ch -> ch == '.').count());
        assertEquals(29l, daysFromNow(response.getBody().validUntil()));
        assertEquals(1L, securityTestsHelper.countSecurityTokens());
    }

    // ---------------------------------------------------------------------------------
    // Login: Failure: Wrong Username
    // ---------------------------------------------------------------------------------
    @Test
    public void login_wrongUsernameFailure_mustReturnError() {
        // Given
        securityTestsHelper.deleteAll();
        securityTestsHelper.createTestUser();

        final String requestBody = "{\"username\":\"Teste User 2\",\"password\":\"1234\"}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        // When
        ResponseEntity<StringErrorResponseRecord> response = restTemplate.postForEntity(API_URL, entity, StringErrorResponseRecord.class);

        // Then
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals(0l, daysFromNow(Timestamp.valueOf(response.getBody().timestamp())));
        assertEquals(HttpStatus.UNAUTHORIZED.value(), response.getBody().status());
        assertEquals("Username is already being used.", response.getBody().error());
        assertEquals(0L, securityTestsHelper.countSecurityTokens());
    }

    // ---------------------------------------------------------------------------------
    // Login: Failure: Wrong Password
    // ---------------------------------------------------------------------------------
    @Test
    public void login_wrongPasswordFailure_mustReturnError() {
        // Given
        securityTestsHelper.deleteAll();
        securityTestsHelper.createTestUser();

        final String requestBody = "{\"username\":\"Test User\",\"password\":\"123456\"}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        // When
        ResponseEntity<StringErrorResponseRecord> response = restTemplate.postForEntity(API_URL, entity, StringErrorResponseRecord.class);

        // Then
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals(0l, daysFromNow(Timestamp.valueOf(response.getBody().timestamp())));
        assertEquals(HttpStatus.UNAUTHORIZED.value(), response.getBody().status());
        assertEquals("Username is already being used.", response.getBody().error());
        assertEquals(0L, securityTestsHelper.countSecurityTokens());
    }

    // ---------------------------------------------------------------------------------
    // Login: Failure: Empty Username
    // ---------------------------------------------------------------------------------
    @Test
    public void login_emptyUsernameFailure_mustReturnError() {
        // Given
        final String requestBody = "{\"username\":\"\",\"password\":\"1234\"}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        // When
        ResponseEntity<StringListErrorResponseRecord> response = restTemplate.postForEntity(API_URL, entity, StringListErrorResponseRecord.class);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(0l, daysFromNow(Timestamp.valueOf(response.getBody().timestamp())));
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getBody().status());
        assertEquals(1, response.getBody().error().size());
        ArrayList<String> errorMessages = new ArrayList<>();
        errorMessages.add("Username is required.");
        assertEquals(errorMessages, response.getBody().error());
        assertEquals(0L, securityTestsHelper.countSecurityTokens());
    }

    // ---------------------------------------------------------------------------------
    // Login: Failure: Absent Username
    // ---------------------------------------------------------------------------------
    @Test
    public void login_absentUsernameFailure_mustReturnError() {
        // Given
        final String requestBody = "{\"password\":\"1234\"}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        // When
        ResponseEntity<StringListErrorResponseRecord> response = restTemplate.postForEntity(API_URL, entity, StringListErrorResponseRecord.class);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(0l, daysFromNow(Timestamp.valueOf(response.getBody().timestamp())));
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getBody().status());
        assertEquals(1, response.getBody().error().size());
        ArrayList<String> errorMessages = new ArrayList<>();
        errorMessages.add("Username is required.");
        assertEquals(errorMessages, response.getBody().error());
        assertEquals(0L, securityTestsHelper.countSecurityTokens());
    }

    // ---------------------------------------------------------------------------------
    // Login: Failure: Empty Password
    // ---------------------------------------------------------------------------------
    @Test
    public void login_emptyPasswordFailure_mustReturnError() {
        // Given
        final String requestBody = "{\"username\":\"Test User\",\"password\":\"\"}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        // When
        ResponseEntity<StringListErrorResponseRecord> response = restTemplate.postForEntity(API_URL, entity, StringListErrorResponseRecord.class);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(0l, daysFromNow(Timestamp.valueOf(response.getBody().timestamp())));
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getBody().status());
        assertEquals(1, response.getBody().error().size());
        ArrayList<String> errorMessages = new ArrayList<>();
        errorMessages.add("Password is required.");
        assertEquals(errorMessages, response.getBody().error());
        assertEquals(0L, securityTestsHelper.countSecurityTokens());
    }

    // ---------------------------------------------------------------------------------
    // Login: Failure: Absent Password
    // ---------------------------------------------------------------------------------
    @Test
    public void login_absentPasswordFailure_mustReturnError() {
        // Given
        final String requestBody = "{\"username\":\"Test User\"}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        // When
        ResponseEntity<StringListErrorResponseRecord> response = restTemplate.postForEntity(API_URL, entity, StringListErrorResponseRecord.class);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(0l, daysFromNow(Timestamp.valueOf(response.getBody().timestamp())));
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getBody().status());
        assertEquals(1, response.getBody().error().size());
        ArrayList<String> errorMessages = new ArrayList<>();
        errorMessages.add("Password is required.");
        assertEquals(errorMessages, response.getBody().error());
        assertEquals(0L, securityTestsHelper.countSecurityTokens());
    }

    // ---------------------------------------------------------------------------------
    // Helper Methods
    // ---------------------------------------------------------------------------------

    private long daysFromNow(final Timestamp timestamp) {
        Timestamp now = new Timestamp(new Date().getTime());
        return Duration.between(now.toInstant(), timestamp.toInstant()).toDays();
    }

}
