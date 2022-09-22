package com.greedobank.reports.service;

import com.greedobank.reports.model.User;
import com.greedobank.reports.model.UserWrapper;
import com.greedobank.reports.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserClient userClient;
    private final JwtUtils jwtUtils;

    @Autowired
    public UserDetailsServiceImpl(
            UserClient userClient,
            JwtUtils jwtUtils) {
        this.userClient = userClient;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public UserDetails loadUserByUsername(String token) throws UsernameNotFoundException {
        String email = jwtUtils.getEmail(token);

        User[] users = userClient.buildRequest(email);

        if (users == null || users.length == 0) {
            throw new UsernameNotFoundException("User not found with such email");
        }
        return new UserWrapper(users[0]);
    }
}
