package com.helospark.site.core.it.security;

import static com.helospark.site.core.it.security.util.SecurityTestHelperUtil.provideRegistrationRequestEntity;
import static com.helospark.site.core.it.security.util.SecurityTestHelperUtil.registerAndLoginAndGetTokenHeader;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.helospark.site.core.Application;
import com.helospark.site.core.it.security.util.DummyNonSecureController;
import com.helospark.site.core.it.security.util.DummySecureController;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = { Application.class, DummySecureController.class,
        DummyNonSecureController.class })
@ActiveProfiles("it")
@TestPropertySource(locations = "classpath:global-it-overrides.properties")
@Sql(scripts = "classpath:/clean-up.sql", executionPhase = AFTER_TEST_METHOD)
public class AuthenticationHappyFlowIT {

    @Test
    public void testRegister(@Autowired TestRestTemplate restTemplate) {
        // GIVEN
        HttpEntity<String> entity = provideRegistrationRequestEntity("user1");

        // WHEN
        ResponseEntity<String> response = restTemplate.exchange("/users/register", HttpMethod.POST, entity, String.class);

        // THEN
        assertThat(response.getStatusCodeValue(), is(200));
    }

    @Test
    public void testRegisterAndLogin(@Autowired TestRestTemplate restTemplate) {
        // GIVEN
        HttpEntity<String> entity = provideRegistrationRequestEntity("user2");
        restTemplate.exchange("/users/register", HttpMethod.POST, entity, Void.class);

        // WHEN
        ResponseEntity<String> result = restTemplate.exchange("/users/login", HttpMethod.POST, entity, String.class);

        // THEN
        assertThat(result.getStatusCodeValue(), is(200));
    }

    @Test
    public void testRegisterAndLoginAndUseSecretResource(@Autowired TestRestTemplate restTemplate) {
        // GIVEN
        HttpEntity<Void> newEntity = registerAndLoginAndGetTokenHeader(restTemplate, "user3");

        // WHEN
        ResponseEntity<String> result = restTemplate.exchange("/secret", HttpMethod.GET, newEntity, String.class);

        // THEN
        assertThat(result.getStatusCodeValue(), is(200));
        assertThat(result.getBody(), is("Secret"));
    }

    @Test
    public void testRegisterAndLoginAndUseSecretResourceWithUserRole(@Autowired TestRestTemplate restTemplate) {
        // GIVEN
        HttpEntity<Void> newEntity = registerAndLoginAndGetTokenHeader(restTemplate, "user4");

        // WHEN
        ResponseEntity<String> result = restTemplate.exchange("/secret_with_user_role", HttpMethod.GET, newEntity, String.class);

        // THEN
        assertThat(result.getStatusCodeValue(), is(200));
        assertThat(result.getBody(), is("Secret with role"));
    }

    @Test
    public void testNonSecureResourceShouldWorkWithoutAuthentication(@Autowired TestRestTemplate restTemplate) {
        // GIVEN

        // WHEN
        ResponseEntity<String> result = restTemplate.getForEntity("/not_secure", String.class);

        // THEN
        assertThat(result.getStatusCodeValue(), is(200));
        assertThat(result.getBody(), is("Not secure"));
    }

    @Test
    public void testNonSecureResourceShouldWorkWithAuthentication(@Autowired TestRestTemplate restTemplate) {
        // GIVEN
        HttpEntity<Void> newEntity = registerAndLoginAndGetTokenHeader(restTemplate, "user5");

        // WHEN
        ResponseEntity<String> result = restTemplate.exchange("/not_secure", HttpMethod.GET, newEntity, String.class);

        // THEN
        assertThat(result.getStatusCodeValue(), is(200));
        assertThat(result.getBody(), is("Not secure"));
    }
}
