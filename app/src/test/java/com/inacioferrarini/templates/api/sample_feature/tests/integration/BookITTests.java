package com.inacioferrarini.templates.api.sample_feature.tests.integration;

import com.inacioferrarini.templates.api.security.tests.SecurityTestsHelper;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class BookITTests {

    private static final String API_URL = "/api/sample/books";

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






}
