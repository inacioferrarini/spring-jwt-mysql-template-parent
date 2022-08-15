package com.inacioferrarini.templates.api;

import com.inacioferrarini.templates.api.base.models.dtos.StringErrorResponseRecord;
import com.inacioferrarini.templates.api.base.models.dtos.StringListErrorResponseRecord;
import com.inacioferrarini.templates.api.security.models.dtos.RegisterUserResponseRecord;
import com.inacioferrarini.templates.api.security.tests.SecurityTestsHelper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
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
public class RegisterUserITTests {

    private static final String API_URL = "/api/security/register";

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private SecurityTestsHelper securityTestsHelper;

    // ---------------------------------------------------------------------------------
    // Setup
    // ---------------------------------------------------------------------------------

    @Before
    public void before() {
        securityTestsHelper.deleteAll();
    }

    @After
    public void after() {
        securityTestsHelper.deleteAll();
    }

    // ---------------------------------------------------------------------------------
    // RegisterUser: Success
    // ---------------------------------------------------------------------------------
    @Test
    public void registerUser_success_mustReturnToken() {
        // Given
        final String requestBody = "{\"username\":\"Test User\",\"email\":\"test.user@email.com\",\"password\":\"1234\"}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        // When
        ResponseEntity<RegisterUserResponseRecord> response = restTemplate.postForEntity(API_URL, entity, RegisterUserResponseRecord.class);

        // Then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Test User", Objects.requireNonNull(response.getBody()).username());
        assertEquals("test.user@email.com", response.getBody().email());
        assertNotNull(response.getBody().token().token());
        assertEquals(2, response.getBody().token().token().chars().filter(ch -> ch == '.').count());
        assertEquals(29L, daysFromNow(response.getBody().token().validUntil()));
        assertEquals(1L, securityTestsHelper.countUsers());
    }

    // ---------------------------------------------------------------------------------
    // RegisterUser: Failure: Duplicated Username
    // ---------------------------------------------------------------------------------
    @Test
    public void registerUser_duplicatedUsernameFailure_mustReturnError() {
        // Given
        securityTestsHelper.createTestUser();

        final String requestBody = "{\"username\":\"Test User\",\"email\":\"test.user@email.com\",\"password\":\"1234\"}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        // When
        ResponseEntity<StringErrorResponseRecord> response = restTemplate.postForEntity(API_URL, entity, StringErrorResponseRecord.class);

        // Then
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals(0L, daysFromNow(Timestamp.valueOf(Objects.requireNonNull(response.getBody()).timestamp())));
        assertEquals(HttpStatus.CONFLICT.value(), response.getBody().status());
        assertEquals("Username is already being used.", response.getBody().error());
        assertEquals(1L, securityTestsHelper.countUsers());
    }

    // ---------------------------------------------------------------------------------
    // RegisterUser: Failure: Duplicated Email
    // ---------------------------------------------------------------------------------
    @Test
    public void registerUser_duplicatedEmailFailure_mustReturnError() {
        // Given
        securityTestsHelper.createTestUser();

        final String requestBody = "{\"username\":\"Test User 2\",\"email\":\"test.user@email.com\",\"password\":\"1234\"}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        // When
        ResponseEntity<StringErrorResponseRecord> response = restTemplate.postForEntity(API_URL, entity, StringErrorResponseRecord.class);

        // Then
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals(0L, daysFromNow(Timestamp.valueOf(Objects.requireNonNull(response.getBody()).timestamp())));
        assertEquals(HttpStatus.CONFLICT.value(), response.getBody().status());
        assertEquals("Email is already being used.", response.getBody().error());
        assertEquals(1L, securityTestsHelper.countUsers());
    }

    // ---------------------------------------------------------------------------------
    // RegisterUser: Failure: Empty Username
    // ---------------------------------------------------------------------------------
    @Test
    public void registerUser_emptyUsernameFailure_mustReturnError() {
        // Given
        final String requestBody = "{\"username\":\"\",\"email\":\"test.user@email.com\",\"password\":\"1234\"}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        // When
        ResponseEntity<StringListErrorResponseRecord> response = restTemplate.postForEntity(API_URL, entity, StringListErrorResponseRecord.class);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(0L, daysFromNow(Timestamp.valueOf(Objects.requireNonNull(response.getBody()).timestamp())));
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getBody().status());
        assertEquals(1, response.getBody().error().size());
        ArrayList<String> errorMessages = new ArrayList<>();
        errorMessages.add("Username is required.");
        assertEquals(errorMessages, response.getBody().error());
        assertEquals(0L, securityTestsHelper.countUsers());
    }

    // ---------------------------------------------------------------------------------
    // RegisterUser: Failure: Absent Username
    // ---------------------------------------------------------------------------------
    @Test
    public void registerUser_absentUsernameFailure_mustReturnError() {
        // Given
        final String requestBody = "{\"email\":\"test.user@email.com\",\"password\":\"1234\"}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        // When
        ResponseEntity<StringListErrorResponseRecord> response = restTemplate.postForEntity(API_URL, entity, StringListErrorResponseRecord.class);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(0L, daysFromNow(Timestamp.valueOf(Objects.requireNonNull(response.getBody()).timestamp())));
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getBody().status());
        assertEquals(1, response.getBody().error().size());
        ArrayList<String> errorMessages = new ArrayList<>();
        errorMessages.add("Username is required.");
        assertEquals(errorMessages, response.getBody().error());
        assertEquals(0L, securityTestsHelper.countUsers());
    }

    // ---------------------------------------------------------------------------------
    // RegisterUser: Failure: Empty Email
    // ---------------------------------------------------------------------------------
    @Test
    public void registerUser_emptyEmailFailure_mustReturnError() {
        // Given
        final String requestBody = "{\"username\":\"Test User\",\"email\":\"\",\"password\":\"1234\"}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        // When
        ResponseEntity<StringListErrorResponseRecord> response = restTemplate.postForEntity(API_URL, entity, StringListErrorResponseRecord.class);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(0L, daysFromNow(Timestamp.valueOf(Objects.requireNonNull(response.getBody()).timestamp())));
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getBody().status());
        assertEquals(1, response.getBody().error().size());
        ArrayList<String> errorMessages = new ArrayList<>();
        errorMessages.add("Email is required.");
        assertEquals(errorMessages, response.getBody().error());
        assertEquals(0L, securityTestsHelper.countUsers());
    }

    // ---------------------------------------------------------------------------------
    // RegisterUser: Failure: Absent Email
    // ---------------------------------------------------------------------------------
    @Test
    public void registerUser_absentEmailFailure_mustReturnError() {
        // Given
        final String requestBody = "{\"username\":\"Test User\",\"password\":\"1234\"}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        // When
        ResponseEntity<StringListErrorResponseRecord> response = restTemplate.postForEntity(API_URL, entity, StringListErrorResponseRecord.class);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(0L, daysFromNow(Timestamp.valueOf(Objects.requireNonNull(response.getBody()).timestamp())));
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getBody().status());
        assertEquals(1, response.getBody().error().size());
        ArrayList<String> errorMessages = new ArrayList<>();
        errorMessages.add("Email is required.");
        assertEquals(errorMessages, response.getBody().error());
        assertEquals(0L, securityTestsHelper.countUsers());
    }

    // ---------------------------------------------------------------------------------
    // RegisterUser: Failure: Empty Password
    // ---------------------------------------------------------------------------------
    @Test
    public void registerUser_emptyPasswordFailure_mustReturnError() {
        // Given
        final String requestBody = "{\"username\":\"Test User\",\"email\":\"test.user@email.com\",\"password\":\"\"}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        // When
        ResponseEntity<StringListErrorResponseRecord> response = restTemplate.postForEntity(API_URL, entity, StringListErrorResponseRecord.class);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(0L, daysFromNow(Timestamp.valueOf(Objects.requireNonNull(response.getBody()).timestamp())));
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getBody().status());
        assertEquals(1, response.getBody().error().size());
        ArrayList<String> errorMessages = new ArrayList<>();
        errorMessages.add("Password is required.");
        assertEquals(errorMessages, response.getBody().error());
        assertEquals(0L, securityTestsHelper.countUsers());
    }

    // ---------------------------------------------------------------------------------
    // RegisterUser: Failure: Absent Password
    // ---------------------------------------------------------------------------------
    @Test
    public void registerUser_absentPasswordFailure_mustReturnError() {
        // Given
        final String requestBody = "{\"username\":\"Test User\",\"email\":\"test.user@email.com\"}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        // When
        ResponseEntity<StringListErrorResponseRecord> response = restTemplate.postForEntity(API_URL, entity, StringListErrorResponseRecord.class);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(0L, daysFromNow(Timestamp.valueOf(Objects.requireNonNull(response.getBody()).timestamp())));
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getBody().status());
        assertEquals(1, response.getBody().error().size());
        ArrayList<String> errorMessages = new ArrayList<>();
        errorMessages.add("Password is required.");
        assertEquals(errorMessages, response.getBody().error());
        assertEquals(0L, securityTestsHelper.countUsers());
    }

    // ---------------------------------------------------------------------------------
    // Helper Methods
    // ---------------------------------------------------------------------------------

    // Mover para Test Utils
    private long daysFromNow(final Timestamp timestamp) {
        Timestamp now = new Timestamp(new Date().getTime());
        return Duration.between(now.toInstant(), timestamp.toInstant()).toDays();
    }

}
