package com.inacioferrarini.templates.api;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class Xpto {

    private static final String API_URL = "/api/security/login";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SecurityTestsHelper securityTestsHelper;

    @Autowired
    private ObjectMapper objectMapper;

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
    // Login: Failure: Wrong Password
    // ---------------------------------------------------------------------------------
    @Test
    public void login_wrongPasswordFailure_mustReturnError() throws Exception {
        // Given
        securityTestsHelper.createTestUser();

        final String requestBody = "{\"username\":\"Test User\",\"password\":\"123456\"}";

        MvcResult result = mockMvc
                .perform(post(API_URL)
                                 .content(requestBody)
                                 .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                                 .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.timestamp", is(CustomMatchers.IsDaysFromNowMatcher(0L))))
                .andExpect(jsonPath("$.status", is(HttpStatus.UNAUTHORIZED.value())))
                .andExpect(jsonPath("$.error", is("Invalid username / password combination.")))
                .andReturn();
        assertEquals(0L, securityTestsHelper.countSecurityTokens());
    }

}
