package com.helospark.site.core.it.security.util;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.helospark.site.core.service.article.user.ApplicationUser;
import com.helospark.site.core.service.article.user.repository.ApplicationUserRepository;
import com.helospark.site.core.web.security.domain.AuthenticationResult;

public class SecurityTestHelperUtil {

    public static HttpEntity<String> provideRegistrationRequestEntity(String userName) {
        String body = "{\"username\":\"" + userName + "\", \"password\":\"password\"}";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(body, headers);
    }

    public static HttpEntity<String> provideRefreshTokenEntity(ResponseEntity<AuthenticationResult> loginResult) {
        String body = "{ \"refreshToken\": \"" + loginResult.getBody().getRefreshToken().getToken() + "\" }";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(body, headers);
    }

    public static void deleteUser(ApplicationUserRepository userRepository, String username) {
        ApplicationUser userToDelete = userRepository.findByUsername(username);
        userRepository.delete(userToDelete);
    }

    public static void changePassword(ApplicationUserRepository userRepository, String username) {
        ApplicationUser user = userRepository.findByUsername(username);
        user.setPassword("NotARealHashButItWillBeGoodForThisTest");
        userRepository.save(user);
    }

    public static HttpEntity<Void> registerAndLoginAndGetTokenHeader(TestRestTemplate restTemplate, String userName) {
        ResponseEntity<AuthenticationResult> loginResult = registerAndGetTokens(restTemplate, userName);
        return createAuthorizationHeader(loginResult);
    }

    public static HttpEntity<Void> createAuthorizationHeader(ResponseEntity<AuthenticationResult> authenticationResult) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + authenticationResult.getBody().getAuthenticationToken().getToken());
        return new HttpEntity<>(headers);
    }

    public static ResponseEntity<AuthenticationResult> registerAndGetTokens(TestRestTemplate restTemplate, String userName) {
        HttpEntity<String> entity = provideRegistrationRequestEntity(userName);
        restTemplate.exchange("/users/register", HttpMethod.POST, entity, Void.class);
        return restTemplate.exchange("/users/login", HttpMethod.POST, entity, AuthenticationResult.class);
    }

}
