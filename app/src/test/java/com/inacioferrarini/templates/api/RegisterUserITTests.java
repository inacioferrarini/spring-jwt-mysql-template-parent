package com.inacioferrarini.templates.api;

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
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class RegisterUserITTests {

    private static final String API_URL = "/api/security/register";

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
    // RegisterUser: Success
    // ---------------------------------------------------------------------------------
    @Test
    public void registerUser_success_mustReturnToken() throws Exception {
        // Given
        final String requestBody = "{\"username\":\"Test User\",\"email\":\"test.user@email.com\",\"password\":\"1234\"}";

        mockMvc.perform(post(API_URL)
                                .content(requestBody)
                                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON))
               .andExpect(status().isCreated())
               .andExpect(jsonPath("$.username", is("Test User")))
               .andExpect(jsonPath("$.email", is("test.user@email.com")))
               .andExpect(jsonPath("$.token.token", is(notNullValue())))
               .andExpect(jsonPath("$.token.valid_until", is(CustomMatchers.IsDaysFromNowMatcher(29L))))
               .andDo(print());
        assertEquals(1L, securityTestsHelper.countUsers());
        assertEquals(1L, securityTestsHelper.countSecurityTokens());
    }

    // ---------------------------------------------------------------------------------
    // RegisterUser: Failure: Duplicated Username
    // ---------------------------------------------------------------------------------
    @Test
    public void registerUser_duplicatedUsernameFailure_mustReturnError() throws Exception {
        // Given
        securityTestsHelper.createTestUser();

        final String requestBody = "{\"username\":\"Test User\",\"email\":\"test.user@email.com\",\"password\":\"1234\"}";

        mockMvc.perform(post(API_URL)
                                .content(requestBody)
                                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON))
               .andExpect(status().isConflict())
               .andExpect(jsonPath("$.timestamp", is(CustomMatchers.IsDaysFromNowMatcher(0L))))
               .andExpect(jsonPath("$.status", is(HttpStatus.CONFLICT.value())))
               .andExpect(jsonPath("$.error", is("Username is already being used.")));
        assertEquals(1L, securityTestsHelper.countUsers());
    }

    // ---------------------------------------------------------------------------------
    // RegisterUser: Failure: Duplicated Email
    // ---------------------------------------------------------------------------------
    @Test
    public void registerUser_duplicatedEmailFailure_mustReturnError() throws Exception {
        // Given
        securityTestsHelper.createTestUser();

        final String requestBody = "{\"username\":\"Test User 2\",\"email\":\"test.user@email.com\",\"password\":\"1234\"}";

        mockMvc.perform(post(API_URL)
                                .content(requestBody)
                                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON))
               .andExpect(status().isConflict())
               .andExpect(jsonPath("$.timestamp", is(CustomMatchers.IsDaysFromNowMatcher(0L))))
               .andExpect(jsonPath("$.status", is(HttpStatus.CONFLICT.value())))
               .andExpect(jsonPath("$.error", is("Email is already being used.")));
        assertEquals(1L, securityTestsHelper.countUsers());
    }

    // ---------------------------------------------------------------------------------
    // RegisterUser: Failure: Empty Username
    // ---------------------------------------------------------------------------------
    @Test
    public void registerUser_emptyUsernameFailure_mustReturnError() throws Exception {
        // Given
        final String requestBody = "{\"username\":\"\",\"email\":\"test.user@email.com\",\"password\":\"1234\"}";

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
        assertEquals(0L, securityTestsHelper.countUsers());
    }

    // ---------------------------------------------------------------------------------
    // RegisterUser: Failure: Absent Username
    // ---------------------------------------------------------------------------------
    @Test
    public void registerUser_absentUsernameFailure_mustReturnError() throws Exception {
        // Given
        final String requestBody = "{\"email\":\"test.user@email.com\",\"password\":\"1234\"}";

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
        assertEquals(0L, securityTestsHelper.countUsers());
    }

    // ---------------------------------------------------------------------------------
    // RegisterUser: Failure: Empty Email
    // ---------------------------------------------------------------------------------
    @Test
    public void registerUser_emptyEmailFailure_mustReturnError() throws Exception {
        // Given
        final String requestBody = "{\"username\":\"Test User\",\"email\":\"\",\"password\":\"1234\"}";

        mockMvc.perform(post(API_URL)
                                .content(requestBody)
                                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON))
               .andExpect(status().isBadRequest())
               .andExpect(jsonPath("$.timestamp", is(CustomMatchers.IsDaysFromNowMatcher(0L))))
               .andExpect(jsonPath("$.status", is(HttpStatus.BAD_REQUEST.value())))
               .andExpect(jsonPath("$.errors").isArray())
               .andExpect(jsonPath("$.errors", hasSize(1)))
               .andExpect(jsonPath("$.errors", hasItem("Email is required.")));
        assertEquals(0L, securityTestsHelper.countUsers());
    }

    // ---------------------------------------------------------------------------------
    // RegisterUser: Failure: Absent Email
    // ---------------------------------------------------------------------------------
    @Test
    public void registerUser_absentEmailFailure_mustReturnError() throws Exception {
        // Given
        final String requestBody = "{\"username\":\"Test User\",\"password\":\"1234\"}";

        mockMvc.perform(post(API_URL)
                                .content(requestBody)
                                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON))
               .andExpect(status().isBadRequest())
               .andExpect(jsonPath("$.timestamp", is(CustomMatchers.IsDaysFromNowMatcher(0L))))
               .andExpect(jsonPath("$.status", is(HttpStatus.BAD_REQUEST.value())))
               .andExpect(jsonPath("$.errors").isArray())
               .andExpect(jsonPath("$.errors", hasSize(1)))
               .andExpect(jsonPath("$.errors", hasItem("Email is required.")));
        assertEquals(0L, securityTestsHelper.countUsers());
    }

    // ---------------------------------------------------------------------------------
    // RegisterUser: Failure: Empty Password
    // ---------------------------------------------------------------------------------
    @Test
    public void registerUser_emptyPasswordFailure_mustReturnError() throws Exception {
        // Given
        final String requestBody = "{\"username\":\"Test User\",\"email\":\"test.user@email.com\",\"password\":\"\"}";

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
        assertEquals(0L, securityTestsHelper.countUsers());
    }

    // ---------------------------------------------------------------------------------
    // RegisterUser: Failure: Absent Password
    // ---------------------------------------------------------------------------------
    @Test
    public void registerUser_absentPasswordFailure_mustReturnError() throws Exception {
        // Given
        final String requestBody = "{\"username\":\"Test User\",\"email\":\"test.user@email.com\"}";

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
        assertEquals(0L, securityTestsHelper.countUsers());
    }

}
