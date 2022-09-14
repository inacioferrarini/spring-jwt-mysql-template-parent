package com.inacioferrarini.templates.api.sample.tests.integration;

import com.inacioferrarini.templates.api.sample.models.entities.BookEntity;
import com.inacioferrarini.templates.api.sample.tests.SampleTestsHelper;
import com.inacioferrarini.templates.api.security.models.entities.UserEntity;
import com.inacioferrarini.templates.api.security.models.records.TokenDataRecord;
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

import java.math.BigDecimal;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

    @Autowired
    private SampleTestsHelper sampleTestsHelper;

    // ---------------------------------------------------------------------------------
    // Setup
    // ---------------------------------------------------------------------------------

    @Before
    public void before() {
        sampleTestsHelper.deleteAll();
        securityTestsHelper.deleteAll();
    }

    @After
    public void after() {
        sampleTestsHelper.deleteAll();
        securityTestsHelper.deleteAll();
    }

    // ---------------------------------------------------------------------------------
    // Find All: Success: With Data
    // ---------------------------------------------------------------------------------
    @Test
    public void findAll_withDataSuccess_mustReturnData() throws Exception {
        // Given
        UserEntity owner = securityTestsHelper.createTestUser();
        TokenDataRecord securityToken = securityTestsHelper.createSecurityToken();
        sampleTestsHelper.createTestBook(owner);

        // When
        mockMvc.perform(get(API_URL)
                                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                                .header(HttpHeaders.AUTHORIZATION, securityToken.token()))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$").isArray())
               .andExpect(jsonPath("$", hasSize(1)))
               .andExpect(jsonPath("$.[0].id", is(notNullValue())))
               .andExpect(jsonPath("$.[0].name", is("Test Book")))
               .andExpect(jsonPath("$.[0].author", is("Test Book Author")))
               .andExpect(jsonPath("$.[0].price", is(10.5)));

        // Then
        assertEquals(1L, sampleTestsHelper.countBooks());
        BookEntity book = sampleTestsHelper.allBooks().get(0);
        assertNotNull(book.getId());
        assertEquals(book.getOwner().getId(), owner.getId());
        assertEquals(book.getName(), "Test Book");
        assertEquals(book.getAuthor(), "Test Book Author");
        assertEquals(book.getPrice().compareTo(new BigDecimal(10.5)), 0);
    }

    // ---------------------------------------------------------------------------------
    // Find All: Success: Without Data
    // ---------------------------------------------------------------------------------
    @Test
    public void findAll_withoutDataSuccess_mustReturnData() throws Exception {
        // Given
        securityTestsHelper.createTestUser();
        TokenDataRecord securityToken = securityTestsHelper.createSecurityToken();

        // When
        mockMvc.perform(get(API_URL)
                                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                                .header(HttpHeaders.AUTHORIZATION, securityToken.token()))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$").isArray())
               .andExpect(jsonPath("$", hasSize(0)));

        // Then
        assertEquals(0L, sampleTestsHelper.countBooks());
    }

    // ---------------------------------------------------------------------------------
    // Find All: Failure: Invalid Access Token
    // ---------------------------------------------------------------------------------
    @Test
    public void findAll_invalidAccessTokenFailure_mustReturnError() throws Exception {
        // Given
        UserEntity owner = securityTestsHelper.createTestUser();
        sampleTestsHelper.createTestBook(owner);

        // When
        mockMvc.perform(get(API_URL)
                                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                                .header(HttpHeaders.AUTHORIZATION, ""))
               .andExpect(status().isUnauthorized());

        // Then
        assertEquals(1L, sampleTestsHelper.countBooks());
    }

    // ---------------------------------------------------------------------------------
    // Find By Id: Success
    // ---------------------------------------------------------------------------------
    @Test
    public void findById_success_mustReturnData() throws Exception {
        // Given
        UserEntity owner = securityTestsHelper.createTestUser();
        TokenDataRecord securityToken = securityTestsHelper.createSecurityToken();
        BookEntity bookEntity = sampleTestsHelper.createTestBook(owner);

        // When
        mockMvc.perform(get(API_URL + "/" + bookEntity.getId())
                                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                                .header(HttpHeaders.AUTHORIZATION, securityToken.token()))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id", is(notNullValue())))
               .andExpect(jsonPath("$.name", is("Test Book")))
               .andExpect(jsonPath("$.author", is("Test Book Author")))
               .andExpect(jsonPath("$.price", is(10.5)));

        // Then
        assertEquals(1L, sampleTestsHelper.countBooks());
        BookEntity book = sampleTestsHelper.allBooks().get(0);
        assertNotNull(book.getId());
        assertEquals(book.getOwner().getId(), owner.getId());
        assertEquals(book.getName(), "Test Book");
        assertEquals(book.getAuthor(), "Test Book Author");
        assertEquals(book.getPrice().compareTo(new BigDecimal(10.5)), 0);
    }

    // ---------------------------------------------------------------------------------
    // Find By Id: Failure: Invalid Access Token
    // ---------------------------------------------------------------------------------
    @Test
    public void findById_invalidAccessTokenFailure_mustReturnError() throws Exception {
        // Given
        UserEntity owner = securityTestsHelper.createTestUser();
        BookEntity bookEntity = sampleTestsHelper.createTestBook(owner);

        // When
        mockMvc.perform(get(API_URL + "/" + bookEntity.getId())
                                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                                .header(HttpHeaders.AUTHORIZATION, ""))
               .andExpect(status().isUnauthorized());

        // Then
        assertEquals(1L, sampleTestsHelper.countBooks());
    }

    // ---------------------------------------------------------------------------------
    // Find By Id: Failure: Not Found
    // ---------------------------------------------------------------------------------
    @Test
    public void findById_notFoundFailure_mustReturnError() throws Exception {
        // Given
        UserEntity owner = securityTestsHelper.createTestUser();
        TokenDataRecord securityToken = securityTestsHelper.createSecurityToken();
        sampleTestsHelper.createTestBook(owner);

        // When
        mockMvc.perform(get(API_URL + "/0")
                                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                                .header(HttpHeaders.AUTHORIZATION, securityToken.token()))
               .andExpect(status().isNotFound());

        // Then
        assertEquals(1L, sampleTestsHelper.countBooks());
    }

    // ---------------------------------------------------------------------------------
    // Find By Id: Failure: Not The Owner
    // ---------------------------------------------------------------------------------
    @Test
    public void findById_notTheOwnerFailure_mustReturnError() throws Exception {
        // Given
        securityTestsHelper.createTestUser();
        UserEntity owner = securityTestsHelper.create2ndTestUser();
        TokenDataRecord securityToken = securityTestsHelper.createSecurityToken();
        BookEntity bookEntity = sampleTestsHelper.createTestBook(owner);

        // When
        mockMvc.perform(get(API_URL + "/" + bookEntity.getId())
                                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                                .header(HttpHeaders.AUTHORIZATION, securityToken.token()))
               .andExpect(status().isNotFound());

        // Then
        assertEquals(1L, sampleTestsHelper.countBooks());
    }

    // ---------------------------------------------------------------------------------
    // Create: Success
    // ---------------------------------------------------------------------------------
    @Test
    public void create_success_mustReturnData() throws Exception {
        // Given
        UserEntity owner = securityTestsHelper.createTestUser();
        TokenDataRecord securityToken = securityTestsHelper.createSecurityToken();

        final String requestBody = "{\"name\":\"Book Name\",\"author\":\"Book Author Name\",\"price\":10.50}";

        // When
        mockMvc.perform(post(API_URL)
                                .content(requestBody)
                                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                                .header(HttpHeaders.AUTHORIZATION, securityToken.token()))
               .andExpect(status().isCreated())
               .andExpect(jsonPath("$.id", is(notNullValue())))
               .andExpect(jsonPath("$.name", is("Book Name")))
               .andExpect(jsonPath("$.author", is("Book Author Name")))
               .andExpect(jsonPath("$.price", is(10.50)));

        // Then
        assertEquals(1L, sampleTestsHelper.countBooks());
        BookEntity book = sampleTestsHelper.allBooks().get(0);
        assertNotNull(book.getId());
        assertEquals(book.getOwner().getId(), owner.getId());
        assertEquals(book.getName(), "Book Name");
        assertEquals(book.getAuthor(), "Book Author Name");
        assertEquals(book.getPrice().compareTo(new BigDecimal(10.5)), 0);
    }

    // ---------------------------------------------------------------------------------
    // Create: Failure: Invalid Access Token
    // ---------------------------------------------------------------------------------
    @Test
    public void create_invalidAccessTokenFailure_mustReturnError() throws Exception {
        // Given
        securityTestsHelper.createTestUser();

        final String requestBody = "{\"name\":\"Book Name\",\"author\":\"Book Author Name\",\"price\":10.50}";

        // When
        mockMvc.perform(post(API_URL)
                                .content(requestBody)
                                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                                .header(HttpHeaders.AUTHORIZATION, ""))
               .andExpect(status().isUnauthorized());

        // Then
        assertEquals(0L, sampleTestsHelper.countBooks());
    }

    // ---------------------------------------------------------------------------------
    // Create: Failure: Validation Error
    // ---------------------------------------------------------------------------------
    @Test
    public void create_validationErrorFailure_mustReturnError() throws Exception {
        // Given
        securityTestsHelper.createTestUser();
        TokenDataRecord securityToken = securityTestsHelper.createSecurityToken();

        final String requestBody = "{}";

        // When
        mockMvc.perform(post(API_URL)
                                .content(requestBody)
                                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                                .header(HttpHeaders.AUTHORIZATION, securityToken.token()))
               .andExpect(status().isBadRequest())
               .andExpect(jsonPath("$.timestamp", is(CustomMatchers.IsDaysFromNowMatcher(0L))))
               .andExpect(jsonPath("$.status", is(HttpStatus.BAD_REQUEST.value())))
               .andExpect(jsonPath("$.errors").isArray())
               .andExpect(jsonPath("$.errors", hasSize(3)))
               .andExpect(jsonPath("$.errors", hasItem("Name is required.")))
               .andExpect(jsonPath("$.errors", hasItem("Author is required.")))
               .andExpect(jsonPath("$.errors", hasItem("Price is required.")));

        // Then
        assertEquals(0L, sampleTestsHelper.countBooks());
    }

    // ---------------------------------------------------------------------------------
    // Create: Failure: Validation Minimal Price Error
    // ---------------------------------------------------------------------------------
    @Test
    public void create_validationMinimalPriceErrorFailure_mustReturnError() throws Exception {
        // Given
        securityTestsHelper.createTestUser();
        TokenDataRecord securityToken = securityTestsHelper.createSecurityToken();

        final String requestBody = "{\"price\":0.0}";

        // When
        mockMvc.perform(post(API_URL)
                                .content(requestBody)
                                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                                .header(HttpHeaders.AUTHORIZATION, securityToken.token()))
               .andExpect(status().isBadRequest())
               .andExpect(jsonPath("$.timestamp", is(CustomMatchers.IsDaysFromNowMatcher(0L))))
               .andExpect(jsonPath("$.status", is(HttpStatus.BAD_REQUEST.value())))
               .andExpect(jsonPath("$.errors").isArray())
               .andExpect(jsonPath("$.errors", hasSize(3)))
               .andExpect(jsonPath("$.errors", hasItem("Name is required.")))
               .andExpect(jsonPath("$.errors", hasItem("Author is required.")))
               .andExpect(jsonPath("$.errors", hasItem("Price must be greater than or equal to 0.01.")));

        // Then
        assertEquals(0L, sampleTestsHelper.countBooks());
    }

    // ---------------------------------------------------------------------------------
    // Update: Success
    // ---------------------------------------------------------------------------------
    @Test
    public void update_success_mustReturnData() throws Exception {
        // Given
        UserEntity owner = securityTestsHelper.createTestUser();
        TokenDataRecord securityToken = securityTestsHelper.createSecurityToken();
        BookEntity bookEntity = sampleTestsHelper.createTestBook(owner);

        final String requestBody = "{\"name\":\"Updated Book Name\",\"author\":\"Updated Book Author Name\",\"price\":20.00}";

        // When
        mockMvc.perform(put(API_URL + "/" + bookEntity.getId())
                                .content(requestBody)
                                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                                .header(HttpHeaders.AUTHORIZATION, securityToken.token()))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id", is(notNullValue())))
               .andExpect(jsonPath("$.name", is("Updated Book Name")))
               .andExpect(jsonPath("$.author", is("Updated Book Author Name")))
               .andExpect(jsonPath("$.price", is(20.0)));

        // Then
        assertEquals(1L, sampleTestsHelper.countBooks());
        BookEntity book = sampleTestsHelper.allBooks().get(0);
        assertNotNull(book.getId());
        assertEquals(book.getOwner().getId(), owner.getId());
        assertEquals(book.getName(), "Updated Book Name");
        assertEquals(book.getAuthor(), "Updated Book Author Name");
        assertEquals(book.getPrice().compareTo(new BigDecimal(20.0)), 0);
    }

    // ---------------------------------------------------------------------------------
    // Update: Failure: Invalid Access Token
    // ---------------------------------------------------------------------------------
    @Test
    public void update_invalidAccessTokenFailure_mustReturnError() throws Exception {
        // Given
        UserEntity owner = securityTestsHelper.createTestUser();
        BookEntity bookEntity = sampleTestsHelper.createTestBook(owner);

        final String requestBody = "{\"name\":\"Updated Book Name\",\"author\":\"Updated Book Author Name\",\"price\":20.00}";

        // When
        mockMvc.perform(put(API_URL + "/" + bookEntity.getId())
                                .content(requestBody)
                                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                                .header(HttpHeaders.AUTHORIZATION, ""))
               .andExpect(status().isUnauthorized());

        // Then
        assertEquals(1L, sampleTestsHelper.countBooks());
        BookEntity book = sampleTestsHelper.allBooks().get(0);
        assertNotNull(book.getId());
        assertEquals(book.getOwner().getId(), owner.getId());
        assertEquals(book.getName(), "Test Book");
        assertEquals(book.getAuthor(), "Test Book Author");
        assertEquals(book.getPrice().compareTo(new BigDecimal(10.5)), 0);
    }

    // ---------------------------------------------------------------------------------
    // Update: Failure: Validation Error
    // ---------------------------------------------------------------------------------
    @Test
    public void update_validationErrorFailure_mustReturnError() throws Exception {
        // Given
        UserEntity owner = securityTestsHelper.createTestUser();
        TokenDataRecord securityToken = securityTestsHelper.createSecurityToken();
        BookEntity bookEntity = sampleTestsHelper.createTestBook(owner);

        final String requestBody = "{}";

        // When
        mockMvc.perform(put(API_URL + "/" + bookEntity.getId())
                                .content(requestBody)
                                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                                .header(HttpHeaders.AUTHORIZATION, securityToken.token()))
               .andExpect(status().isBadRequest())
               .andExpect(jsonPath("$.timestamp", is(CustomMatchers.IsDaysFromNowMatcher(0L))))
               .andExpect(jsonPath("$.status", is(HttpStatus.BAD_REQUEST.value())))
               .andExpect(jsonPath("$.errors").isArray())
               .andExpect(jsonPath("$.errors", hasSize(3)))
               .andExpect(jsonPath("$.errors", hasItem("Name is required.")))
               .andExpect(jsonPath("$.errors", hasItem("Author is required.")))
               .andExpect(jsonPath("$.errors", hasItem("Price is required.")));

        // Then
        assertEquals(1L, sampleTestsHelper.countBooks());
    }

    // ---------------------------------------------------------------------------------
    // Update: Failure: Validation Minimal Price Error
    // ---------------------------------------------------------------------------------
    @Test
    public void update_validationMinimalPriceErrorFailure_mustReturnError() throws Exception {
        // Given
        UserEntity owner = securityTestsHelper.createTestUser();
        TokenDataRecord securityToken = securityTestsHelper.createSecurityToken();
        BookEntity bookEntity = sampleTestsHelper.createTestBook(owner);

        final String requestBody = "{\"price\":0.0}";

        // When
        mockMvc.perform(put(API_URL + "/" + bookEntity.getId())
                                .content(requestBody)
                                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                                .header(HttpHeaders.AUTHORIZATION, securityToken.token()))
               .andExpect(status().isBadRequest())
               .andExpect(jsonPath("$.timestamp", is(CustomMatchers.IsDaysFromNowMatcher(0L))))
               .andExpect(jsonPath("$.status", is(HttpStatus.BAD_REQUEST.value())))
               .andExpect(jsonPath("$.errors").isArray())
               .andExpect(jsonPath("$.errors", hasSize(3)))
               .andExpect(jsonPath("$.errors", hasItem("Name is required.")))
               .andExpect(jsonPath("$.errors", hasItem("Author is required.")))
               .andExpect(jsonPath("$.errors", hasItem("Price must be greater than or equal to 0.01.")));

        // Then
        assertEquals(1L, sampleTestsHelper.countBooks());
    }

    // ---------------------------------------------------------------------------------
    // Update: Failure: Not Found
    // ---------------------------------------------------------------------------------
    @Test
    public void update_notFoundFailure_mustReturnError() throws Exception {
        // Given
        UserEntity owner = securityTestsHelper.createTestUser();
        TokenDataRecord securityToken = securityTestsHelper.createSecurityToken();
        sampleTestsHelper.createTestBook(owner);

        final String requestBody = "{\"name\":\"Updated Book Name\",\"author\":\"Updated Book Author Name\",\"price\":20.00}";

        // When
        mockMvc.perform(put(API_URL + "/0")
                                .content(requestBody)
                                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                                .header(HttpHeaders.AUTHORIZATION, securityToken.token()))
               .andExpect(status().isNotFound());

        // Then
        assertEquals(1L, sampleTestsHelper.countBooks());
    }

    // ---------------------------------------------------------------------------------
    // Update: Failure: Not The Owner
    // ---------------------------------------------------------------------------------
    @Test
    public void update_notTheOwnerFailure_mustReturnError() throws Exception {
        // Given
        securityTestsHelper.createTestUser();
        UserEntity owner = securityTestsHelper.create2ndTestUser();
        TokenDataRecord securityToken = securityTestsHelper.createSecurityToken();
        BookEntity bookEntity = sampleTestsHelper.createTestBook(owner);

        final String requestBody = "{\"name\":\"Updated Book Name\",\"author\":\"Updated Book Author Name\",\"price\":20.00}";

        // When
        mockMvc.perform(put(API_URL + "/" + bookEntity.getId())
                                .content(requestBody)
                                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                                .header(HttpHeaders.AUTHORIZATION, securityToken.token()))
               .andExpect(status().isNotFound());

        // Then
        assertEquals(1L, sampleTestsHelper.countBooks());
        BookEntity book = sampleTestsHelper.allBooks().get(0);
        assertNotNull(book.getId());
        assertEquals(book.getOwner().getId(), owner.getId());
        assertEquals(book.getName(), "Test Book");
        assertEquals(book.getAuthor(), "Test Book Author");
        assertEquals(book.getPrice().compareTo(new BigDecimal(10.5)), 0);
    }

    // ---------------------------------------------------------------------------------
    // Delete: Success
    // ---------------------------------------------------------------------------------
    @Test
    public void delete_success_mustReturnData() throws Exception {
        // Given
        UserEntity owner = securityTestsHelper.createTestUser();
        TokenDataRecord securityToken = securityTestsHelper.createSecurityToken();
        BookEntity bookEntity = sampleTestsHelper.createTestBook(owner);

        // When
        mockMvc.perform(delete(API_URL + "/" + bookEntity.getId())
                                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                                .header(HttpHeaders.AUTHORIZATION, securityToken.token()))
               .andExpect(status().isNoContent());

        // Then
        assertEquals(0L, sampleTestsHelper.countBooks());
    }

    // ---------------------------------------------------------------------------------
    // Delete: Failure: Invalid Access Token
    // ---------------------------------------------------------------------------------
    @Test
    public void delete_invalidAccessTokenFailure_mustReturnError() throws Exception {
        // Given
        UserEntity owner = securityTestsHelper.createTestUser();
        BookEntity bookEntity = sampleTestsHelper.createTestBook(owner);

        // When
        mockMvc.perform(delete(API_URL + "/" + bookEntity.getId())
                                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                                .header(HttpHeaders.AUTHORIZATION, ""))
               .andExpect(status().isUnauthorized());

        // Then
        assertEquals(1L, sampleTestsHelper.countBooks());
    }

    // ---------------------------------------------------------------------------------
    // Find By Id: Failure: Not Found
    // ---------------------------------------------------------------------------------
    @Test
    public void delete_notFoundFailure_mustReturnError() throws Exception {
        // Given
        UserEntity owner = securityTestsHelper.createTestUser();
        TokenDataRecord securityToken = securityTestsHelper.createSecurityToken();
        sampleTestsHelper.createTestBook(owner);

        // When
        mockMvc.perform(delete(API_URL + "/0")
                                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                                .header(HttpHeaders.AUTHORIZATION, securityToken.token()))
               .andExpect(status().isNotFound());

        // Then
        assertEquals(1L, sampleTestsHelper.countBooks());
    }

    // ---------------------------------------------------------------------------------
    // Find By Id: Failure: Not The Owner
    // ---------------------------------------------------------------------------------
    @Test
    public void delete_notTheOwnerFailure_mustReturnError() throws Exception {
        // Given
        securityTestsHelper.createTestUser();
        UserEntity owner = securityTestsHelper.create2ndTestUser();
        TokenDataRecord securityToken = securityTestsHelper.createSecurityToken();
        BookEntity bookEntity = sampleTestsHelper.createTestBook(owner);

        // When
        mockMvc.perform(delete(API_URL + "/" + bookEntity.getId())
                                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                                .header(HttpHeaders.AUTHORIZATION, securityToken.token()))
               .andExpect(status().isNotFound());

        // Then
        assertEquals(1L, sampleTestsHelper.countBooks());
    }

}
