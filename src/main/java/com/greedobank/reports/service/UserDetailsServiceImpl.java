package com.greedobank.reports.service;

import com.greedobank.reports.model.User;
import com.greedobank.reports.model.UserWrapper;
import com.greedobank.reports.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final RestTemplate restTemplate;
    private final String resourceUrl = "http://localhost:8081/api/v1/users/?email=";

    @Autowired
    public UserDetailsServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public UserDetails loadUserByUsername(String token) throws UsernameNotFoundException {
        String email = JwtUtils.getEmail(token);
        String request = resourceUrl.concat(email);

        HttpEntity<String> bodyOfToken = new HttpEntity<>(token);
        // ResponseEntity<User[]> entity = restTemplate.exchange(request, HttpMethod.GET, bodyOfToken, User[].class);

        ResponseEntity<User[]> entity = restTemplate.getForEntity(request, User[].class);

        User[] users = entity.getBody();

        if (users == null || users.length == 0) {
            throw new UsernameNotFoundException("User not found with such email");
        }
        return new UserWrapper(users[0]);
    }
}
