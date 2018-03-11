package com.helospark.site.core.it.security;

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
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.helospark.site.core.Application;
import com.helospark.site.core.it.security.util.DummySecureController;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = { Application.class, DummySecureController.class })
@ActiveProfiles("it")
@TestPropertySource(locations = "classpath:global-it-overrides.properties")
public class AuthenticationErrorFlowIT {

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

}
