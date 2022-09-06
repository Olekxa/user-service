package com.greedobank.reports.service;

import com.greedobank.reports.model.User;
import com.greedobank.reports.model.UserWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final RestTemplate restTemplate;
    private final String fooResourceUrl = "http://localhost:8081/api/v1/users/";

    @Autowired
    public UserDetailsServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        ResponseEntity<User[]> entity = restTemplate.getForEntity("http://localhost:8081/api/v1/users/" + email, User[].class);

        User[] users = entity.getBody();
        if (users == null || users.length == 0) {
            throw new UsernameNotFoundException("");
        }
        return new UserWrapper(users[0]);
    }
}
