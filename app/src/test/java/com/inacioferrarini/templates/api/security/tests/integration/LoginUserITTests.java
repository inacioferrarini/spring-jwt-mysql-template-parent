package com.inacioferrarini.templates.api.security.tests.integration;

import com.inacioferrarini.templates.api.security.tests.SecurityTestsHelper;
import com.inacioferrarini.test.hamcrest.matchers.CustomMatchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class LoginUserITTests {

    private static final String API_URL = "/api/security/login";

    @Autowired
    private MockMvc mockMvc;

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
    // Login: Success
    // ---------------------------------------------------------------------------------
    @Test
    public void login_success_mustReturnToken() throws Exception {
        // Given
        securityTestsHelper.createTestUser();

        final String requestBody = "{\"username\":\"Test User\",\"password\":\"1234\"}";

        mockMvc.perform(post(API_URL)
                                .content(requestBody)
                                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.token", is(notNullValue())))
               .andExpect(jsonPath("$.valid_until", is(CustomMatchers.IsDaysFromNowMatcher(29L))));
        assertEquals(1L, securityTestsHelper.countSecurityTokens());
    }

    // ---------------------------------------------------------------------------------
    // Login: Failure: Wrong Username
    // ---------------------------------------------------------------------------------
    @Test
    public void login_wrongUsernameFailure_mustReturnError() throws Exception {
        // Given
        securityTestsHelper.createTestUser();

        final String requestBody = "{\"username\":\"Teste User 2\",\"password\":\"1234\"}";

        mockMvc.perform(post(API_URL)
                                .content(requestBody)
                                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON))
               .andExpect(status().isUnauthorized())
               .andExpect(jsonPath("$.timestamp", is(CustomMatchers.IsDaysFromNowMatcher(0L))))
               .andExpect(jsonPath("$.status", is(HttpStatus.UNAUTHORIZED.value())))
               .andExpect(jsonPath("$.error", is("Invalid username / password combination.")));
        assertEquals(0L, securityTestsHelper.countSecurityTokens());
    }

    // ---------------------------------------------------------------------------------
    // Login: Failure: Wrong Password
    // ---------------------------------------------------------------------------------
    @Test
    public void login_wrongPasswordFailure_mustReturnError() throws Exception {
        // Given
        securityTestsHelper.createTestUser();

        final String requestBody = "{\"username\":\"Test User\",\"password\":\"123456\"}";

        mockMvc.perform(post(API_URL)
                                .content(requestBody)
                                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON))
               .andExpect(status().isUnauthorized())
               .andExpect(jsonPath("$.timestamp", is(CustomMatchers.IsDaysFromNowMatcher(0L))))
               .andExpect(jsonPath("$.status", is(HttpStatus.UNAUTHORIZED.value())))
               .andExpect(jsonPath("$.error", is("Invalid username / password combination.")));
        assertEquals(0L, securityTestsHelper.countSecurityTokens());
    }

    // ---------------------------------------------------------------------------------
    // Login: Failure: Empty Username
    // ---------------------------------------------------------------------------------
    @Test
    public void login_emptyUsernameFailure_mustReturnError() throws Exception {
        // Given
        final String requestBody = "{\"username\":\"\",\"password\":\"1234\"}";

        mockMvc.perform(post(API_URL)
                                .content(requestBody)
                                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON))
               .andExpect(status().isBadRequest())
               .andExpect(jsonPath("$.timestamp", is(CustomMatchers.IsDaysFromNowMatcher(0L))))
               .andExpect(jsonPath("$.status", is(HttpStatus.BAD_REQUEST.value())))
               .andExpect(jsonPath("$.errors").isArray())
               .andExpect(jsonPath("$.errors", hasSize(1)))
               .andExpect(jsonPath("$.errors", hasItem("Username is required.")));
        assertEquals(0L, securityTestsHelper.countSecurityTokens());
    }

    // ---------------------------------------------------------------------------------
    // Login: Failure: Absent Username
    // ---------------------------------------------------------------------------------
    @Test
    public void login_absentUsernameFailure_mustReturnError() throws Exception {
        // Given
        final String requestBody = "{\"password\":\"1234\"}";

        mockMvc.perform(post(API_URL)
                                .content(requestBody)
                                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON))
               .andExpect(status().isBadRequest())
               .andExpect(jsonPath("$.timestamp", is(CustomMatchers.IsDaysFromNowMatcher(0L))))
               .andExpect(jsonPath("$.status", is(HttpStatus.BAD_REQUEST.value())))
               .andExpect(jsonPath("$.errors").isArray())
               .andExpect(jsonPath("$.errors", hasSize(1)))
               .andExpect(jsonPath("$.errors", hasItem("Username is required.")));
        assertEquals(0L, securityTestsHelper.countSecurityTokens());
    }

    // ---------------------------------------------------------------------------------
    // Login: Failure: Empty Password
    // ---------------------------------------------------------------------------------
    @Test
    public void login_emptyPasswordFailure_mustReturnError() throws Exception {
        // Given
        final String requestBody = "{\"username\":\"Test User\",\"password\":\"\"}";

        mockMvc.perform(post(API_URL)
                                .content(requestBody)
                                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON))
               .andExpect(status().isBadRequest())
               .andExpect(jsonPath("$.timestamp", is(CustomMatchers.IsDaysFromNowMatcher(0L))))
               .andExpect(jsonPath("$.status", is(HttpStatus.BAD_REQUEST.value())))
               .andExpect(jsonPath("$.errors").isArray())
               .andExpect(jsonPath("$.errors", hasSize(1)))
               .andExpect(jsonPath("$.errors", hasItem("Password is required.")));
        assertEquals(0L, securityTestsHelper.countSecurityTokens());
    }

    // ---------------------------------------------------------------------------------
    // Login: Failure: Absent Password
    // ---------------------------------------------------------------------------------
    @Test
    public void login_absentPasswordFailure_mustReturnError() throws Exception {
        // Given
        final String requestBody = "{\"username\":\"Test User\"}";

        mockMvc.perform(post(API_URL)
                                .content(requestBody)
                                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON))
               .andExpect(status().isBadRequest())
               .andExpect(jsonPath("$.timestamp", is(CustomMatchers.IsDaysFromNowMatcher(0L))))
               .andExpect(jsonPath("$.status", is(HttpStatus.BAD_REQUEST.value())))
               .andExpect(jsonPath("$.errors").isArray())
               .andExpect(jsonPath("$.errors", hasSize(1)))
               .andExpect(jsonPath("$.errors", hasItem("Password is required.")));
        assertEquals(0L, securityTestsHelper.countSecurityTokens());
    }

}
