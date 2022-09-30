package com.greedobank.reports.client;

import com.greedobank.reports.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Component
public class UserClient {
    private final RestTemplate restTemplate;
    private final String url;

    public UserClient(RestTemplate restTemplate, @Value("${userService.url}") String url) {
        this.restTemplate = restTemplate;
        this.url = url;
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
}
