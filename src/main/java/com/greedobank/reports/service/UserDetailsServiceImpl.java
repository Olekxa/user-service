package com.greedobank.reports.service;

import com.greedobank.reports.model.User;
import com.greedobank.reports.model.UserWrapper;
import com.greedobank.reports.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final RestTemplate restTemplate;
    private final String url = "http://localhost:8081/api/v1/users/";

    @Autowired
    public UserDetailsServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }



    @Override
    public UserDetails loadUserByUsername(String token) throws UsernameNotFoundException {
        String email = JwtUtils.getEmail(token);
        String tokenWith = "Bearer ".concat(token);
        String request = url.concat("?email=").concat(email);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        headers.set("Authorization", tokenWith);


        HttpEntity<String> jwtEntity = new HttpEntity<>(headers);
        ResponseEntity<User[]> entity = restTemplate.exchange(request, HttpMethod.GET, jwtEntity,
                User[].class);

        // ResponseEntity<User[]> entity = restTemplate.exchange(request, HttpMethod.GET, bodyOfToken, User[].class);

        // ResponseEntity<User[]> entity = restTemplate.getForEntity(request, User[].class);

        User[] users = entity.getBody();

        if (users == null || users.length == 0) {
            throw new UsernameNotFoundException("User not found with such email");
        }
        return new UserWrapper(users[0]);
    }
}
