package com.greedobank.reports.service;

import com.greedobank.reports.model.User;
import com.greedobank.reports.model.UserWrapper;
import com.greedobank.reports.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final String url;
    private final RestTemplate restTemplate;
    private final JwtUtils jwtUtils;

    @Autowired
    public UserDetailsServiceImpl(
            @Value("${userService.url}") String url,
            RestTemplate restTemplate,
            JwtUtils jwtUtils) {
        this.url = url;
        this.restTemplate = restTemplate;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public UserDetails loadUserByUsername(String token) throws UsernameNotFoundException {
        String email = jwtUtils.getEmail(token);
        String request = url.concat(email);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<User[]> response = restTemplate.exchange(request, HttpMethod.GET, entity, User[].class);

        User[] users = response.getBody();

        if (users == null || users.length == 0) {
            throw new UsernameNotFoundException("User not found with such email");
        }
        return new UserWrapper(users[0]);
    }
}
