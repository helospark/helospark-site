package com.helospark.site.core.it.security;

import static com.helospark.site.core.it.security.util.SecurityTestHelperUtil.changePassword;
import static com.helospark.site.core.it.security.util.SecurityTestHelperUtil.createAuthorizationHeader;
import static com.helospark.site.core.it.security.util.SecurityTestHelperUtil.deleteUser;
import static com.helospark.site.core.it.security.util.SecurityTestHelperUtil.provideRefreshTokenEntity;
import static com.helospark.site.core.it.security.util.SecurityTestHelperUtil.registerAndGetTokens;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.springframework.http.HttpStatus.FORBIDDEN;
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
import com.helospark.site.core.it.security.util.DummySecureController;
import com.helospark.site.core.service.article.user.repository.ApplicationUserRepository;
import com.helospark.site.core.web.security.domain.AuthenticationResult;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = { Application.class, DummySecureController.class })
@ActiveProfiles("it")
@TestPropertySource(locations = "classpath:global-it-overrides.properties")
@Sql(scripts = "classpath:/clean-up.sql", executionPhase = AFTER_TEST_METHOD)
public class RefreshTokenIT {

    @Test
    public void testRegisterAndRefreshToken(@Autowired TestRestTemplate restTemplate) {
        // GIVEN
        ResponseEntity<AuthenticationResult> loginResult = registerAndGetTokens(restTemplate, "user5");
        HttpEntity<String> newEntity = provideRefreshTokenEntity(loginResult);

        // WHEN
        ResponseEntity<AuthenticationResult> result = restTemplate.exchange("/users/login/refresh", HttpMethod.POST, newEntity,
                AuthenticationResult.class);

        // THEN
        assertThat(result.getStatusCodeValue(), is(200));
    }

    @Test
    public void testRefreshAndCallSecureController(@Autowired TestRestTemplate restTemplate) {
        // GIVEN
        ResponseEntity<AuthenticationResult> loginResult = registerAndGetTokens(restTemplate, "user6");
        HttpEntity<String> newEntity = provideRefreshTokenEntity(loginResult);
        ResponseEntity<AuthenticationResult> refreshed = restTemplate.exchange("/users/login/refresh", HttpMethod.POST, newEntity,
                AuthenticationResult.class);
        HttpEntity<Void> refreshedHeader = createAuthorizationHeader(refreshed);

        // WHEN
        ResponseEntity<String> result = restTemplate.exchange("/secret", HttpMethod.GET, refreshedHeader, String.class);

        // THEN
        assertThat(result.getStatusCodeValue(), is(200));
        assertThat(result.getBody(), is("Secret"));
    }

    @Test
    public void testRefreshWithChangedPassword(@Autowired TestRestTemplate restTemplate, @Autowired ApplicationUserRepository userRepository) {
        // GIVEN
        String username = "user7";
        ResponseEntity<AuthenticationResult> loginResult = registerAndGetTokens(restTemplate, username);
        changePassword(userRepository, username);
        HttpEntity<String> newEntity = provideRefreshTokenEntity(loginResult);

        // WHEN
        ResponseEntity<String> result = restTemplate.exchange("/users/login/refresh", HttpMethod.POST, newEntity, String.class);

        // THEN
        assertThat(result.getStatusCode(), is(FORBIDDEN));
    }

    @Test
    public void testRefreshWithDeletedUser(@Autowired TestRestTemplate restTemplate, @Autowired ApplicationUserRepository userRepository) {
        // GIVEN
        String username = "user8";
        ResponseEntity<AuthenticationResult> loginResult = registerAndGetTokens(restTemplate, username);
        deleteUser(userRepository, username);
        HttpEntity<String> newEntity = provideRefreshTokenEntity(loginResult);

        // WHEN
        ResponseEntity<String> result = restTemplate.exchange("/users/login/refresh", HttpMethod.POST, newEntity, String.class);

        // THEN
        assertThat(result.getStatusCode(), is(FORBIDDEN));
    }

}
