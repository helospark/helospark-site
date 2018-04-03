package com.helospark.site.core.it.security;

import static com.helospark.site.core.it.security.util.SecurityTestHelperUtil.provideRegistrationRequestEntity;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsString;
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
public class RegistrationErrorIT {

    @Test
    public void testRegisterWithShortPassword(@Autowired TestRestTemplate restTemplate) {
        // GIVEN
        HttpEntity<String> entity = provideRegistrationRequestEntity("user1", "pass", "pass");

        // WHEN
        ResponseEntity<String> response = restTemplate.exchange("/users/register", HttpMethod.POST, entity, String.class);

        // THEN
        assertThat(response.getStatusCodeValue(), is(400));
        assertThat(response.getBody(), containsString("must be at least 6 characters"));
    }

    @Test
    public void testRegisterWithDifferentPassword(@Autowired TestRestTemplate restTemplate) {
        // GIVEN
        HttpEntity<String> entity = provideRegistrationRequestEntity("user1", "pass", "pass2");

        // WHEN
        ResponseEntity<String> response = restTemplate.exchange("/users/register", HttpMethod.POST, entity, String.class);

        // THEN
        assertThat(response.getStatusCodeValue(), is(400));
        assertThat(response.getBody(), containsString("Passwords do not match"));
    }
}
