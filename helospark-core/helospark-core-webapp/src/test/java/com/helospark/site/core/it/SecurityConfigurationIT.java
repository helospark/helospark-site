package com.helospark.site.core.it;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.helospark.site.core.Application;
import com.helospark.site.core.it.SecurityConfigurationIT.DummySecureController;
import com.helospark.site.core.web.security.domain.AuthenticationResult;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = { Application.class, DummySecureController.class })
@ActiveProfiles("it")
@TestPropertySource(locations = "classpath:global-it-overrides.properties")
public class SecurityConfigurationIT {

    @Test
    public void testRegister(@Autowired TestRestTemplate restTemplate) {
        // GIVEN
        HttpEntity<String> entity = getDataUserBody("user1");

        // WHEN
        ResponseEntity<Void> response = restTemplate.exchange("/users/register", HttpMethod.POST, entity, Void.class);

        // THEN
        assertThat(response.getStatusCodeValue(), is(200));
    }

    @Test
    public void testRegisterAndLogin(@Autowired TestRestTemplate restTemplate) {
        // GIVEN
        HttpEntity<String> entity = getDataUserBody("user2");
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
    public void testRegisterAndRefreshToken(@Autowired TestRestTemplate restTemplate) {
        // GIVEN
        ResponseEntity<AuthenticationResult> loginResult = registerAndGetTokens(restTemplate, "user5");
        HttpEntity<String> newEntity = createRefreshTokenEntity(loginResult);

        // WHEN
        ResponseEntity<AuthenticationResult> result = restTemplate.exchange("/users/login/refresh", HttpMethod.POST, newEntity,
                AuthenticationResult.class);

        // THEN
        assertThat(result.getStatusCodeValue(), is(200));
    }

    private HttpEntity<String> createRefreshTokenEntity(ResponseEntity<AuthenticationResult> loginResult) {
        String body = "{ \"refreshToken\": \"" + loginResult.getBody().getRefreshToken().getToken() + "\" }";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(body, headers);
    }

    private HttpEntity<Void> registerAndLoginAndGetTokenHeader(TestRestTemplate restTemplate, String userName) {
        ResponseEntity<AuthenticationResult> loginResult = registerAndGetTokens(restTemplate, userName);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + loginResult.getBody().getAuthenticationToken().getToken());
        return new HttpEntity<>(headers);
    }

    private ResponseEntity<AuthenticationResult> registerAndGetTokens(TestRestTemplate restTemplate, String userName) {
        HttpEntity<String> entity = getDataUserBody(userName);
        restTemplate.exchange("/users/register", HttpMethod.POST, entity, Void.class);
        ResponseEntity<AuthenticationResult> loginResult = restTemplate.exchange("/users/login", HttpMethod.POST, entity, AuthenticationResult.class);
        return loginResult;
    }

    @Test
    public void testSecretResourceWithoutLogin(@Autowired TestRestTemplate restTemplate) {
        // GIVEN

        // WHEN
        ResponseEntity<String> result = restTemplate.getForEntity("/secret", String.class);

        // THEN
        assertThat(result.getStatusCodeValue(), is(403));
    }

    @Test
    public void testSecretResourceWithWrongCrediential(@Autowired TestRestTemplate restTemplate) {
        // GIVEN
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + "faketoken");
        HttpEntity<Void> newEntity = new HttpEntity<>(headers);

        // WHEN
        ResponseEntity<String> result = restTemplate.exchange("/secret", HttpMethod.GET, newEntity, String.class);

        // THEN
        assertThat(result.getStatusCodeValue(), is(403));
    }

    @Test
    public void testSecretResourceWithWrongCredientialWithExpiredToken(@Autowired TestRestTemplate restTemplate) {
        // GIVEN
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer "
                + "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyMyIsImV4cCI6MTUxODM2MTY0MH0.1f8jb9jUtCZNzE1Go33aSRw7ITD9iUNV_2tILFb7cPumZOPk8OQglpFD_Qu0QwtW_q15toS7txKUYo1fANMmPQ");
        HttpEntity<Void> newEntity = new HttpEntity<>(headers);

        // WHEN
        ResponseEntity<String> result = restTemplate.exchange("/secret", HttpMethod.GET, newEntity, String.class);

        // THEN
        assertThat(result.getStatusCodeValue(), is(403));
    }

    @Test
    public void testSecretResourceWithWrongCredientialWithWrongSecretKey(@Autowired TestRestTemplate restTemplate) {
        // GIVEN
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer "
                + "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyMyIsImV4cCI6MTUxOTIyOTE4OH0.faTlY4wbK_whWoRhy8A4Sz5qw5tkYgLQyk3FtCMZ1HxbIVg3uGa_Q0bKpgP0qvMnM01h9_TghYQ33iI7wOJ39g");
        HttpEntity<Void> newEntity = new HttpEntity<>(headers);

        // WHEN
        ResponseEntity<String> result = restTemplate.exchange("/secret", HttpMethod.GET, newEntity, String.class);

        // THEN
        assertThat(result.getStatusCodeValue(), is(403));
    }

    private HttpEntity<String> getDataUserBody(String userName) {
        String body = "{\"username\":\"" + userName + "\", \"password\":\"password\"}";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(body, headers);
        return entity;
    }

    @RestController
    static class DummySecureController {

        @PreAuthorize("isAuthenticated()")
        @GetMapping("/secret")
        public String secret() {
            return "Secret";
        }

        @PreAuthorize("hasRole('ROLE_USER')")
        @GetMapping("/secret_with_user_role")
        public String secretWithRole() {
            return "Secret with role";
        }
    }
}
