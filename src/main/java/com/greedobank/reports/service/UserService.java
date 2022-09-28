package com.greedobank.reports.service;

import com.greedobank.reports.client.UserClient;
import com.greedobank.reports.model.User;
import com.greedobank.reports.model.UserWrapper;
import com.greedobank.reports.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    private final UserClient userClient;
    private final JwtUtils jwtUtils;

    @Autowired
    public UserService(UserClient userClient, JwtUtils jwtUtils) {
        this.userClient = userClient;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public UserDetails loadUserByUsername(String token) throws UsernameNotFoundException {
        String email = jwtUtils.getEmail(token);

        User[] users = userClient.getUserByEmail(email);

        if (users == null || users.length == 0) {
            throw new UsernameNotFoundException("User was not found by email " + email);
        }
        return new UserWrapper(users[0]);
    }
}
