package com.greedobank.reports.client;

import com.greedobank.reports.model.AccessToken;
import com.greedobank.reports.model.JsonResponse;
import com.greedobank.reports.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class UserClient {
    private final RestTemplate restTemplate;
    private final String url;
    private final String urlAuth;
    private final String serviceLogin;
    private final String passwordLogin;


    public UserClient(RestTemplate restTemplate,
                      @Value("${userService.url}") String url,
                      @Value("${userServiceAuth.url}") String urlAuth,
                      @Value("${serviceAdmin.username}") String serviceLogin,
                      @Value("${serviceAdmin.password}") String passwordLogin) {
        this.restTemplate = restTemplate;
        this.url = url;
        this.urlAuth = urlAuth;
        this.serviceLogin = serviceLogin;
        this.passwordLogin = passwordLogin;
    }

    public User[] getUserByEmail(String email) {
        String request = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("email", email)
                .encode()
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<User[]> response = restTemplate.exchange(request, HttpMethod.GET, entity, User[].class);
        return response.getBody();
    }

    public User[] getMailsOfUsers() throws UsernameNotFoundException {
        String token = getTokenFromService()
                .orElseThrow(() -> new UsernameNotFoundException("User was not found"))
                .getAccessToken();

        String request = UriComponentsBuilder.fromHttpUrl(url)
                .encode()
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        headers.setBearerAuth(token);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<User[]> response = restTemplate.exchange(request, HttpMethod.GET, entity, User[].class);
        return response.getBody();
    }

    private Optional<AccessToken> getTokenFromService() {
        String request = UriComponentsBuilder.fromHttpUrl(urlAuth)
                .encode()
                .toUriString();

        Map<String, String> json = new HashMap<>();
        json.put("email", serviceLogin);
        json.put("password", passwordLogin);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        HttpEntity<?> entity = new HttpEntity<>(json, headers);
        ResponseEntity<JsonResponse<AccessToken>> response = restTemplate.exchange(
                request,
                HttpMethod.POST,
                entity,
                new ParameterizedTypeReference<>() {
                });
        return Optional.ofNullable(response.getBody()).map(JsonResponse::getContent);
    }
}
