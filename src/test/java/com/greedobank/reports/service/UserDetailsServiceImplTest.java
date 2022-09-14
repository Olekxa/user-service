package com.greedobank.reports.service;

import com.greedobank.reports.model.Role;
import com.greedobank.reports.model.RoleTitle;
import com.greedobank.reports.model.User;
import com.greedobank.reports.utils.JwtUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserDetailsServiceImplTest {
    private UserDetailsServiceImpl service;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private JwtUtils jwtUtils;

    @BeforeEach
    private void setUp() {
        MockitoAnnotations.openMocks(this);
        String url = "";
        service = new UserDetailsServiceImpl(url, restTemplate, jwtUtils);
    }

    @Test
    public void shouldReturnMail() {
        String token = "";
        String mail = "dzhmur@griddynamics.com";
        User user = new User(1, "", "", mail, new Role(1, RoleTitle.ROLE_ADMIN));
        User[] array = {user};
        ResponseEntity<User[]> responseEntity = new ResponseEntity<>(array, HttpStatus.ACCEPTED);
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(headers);

        when(jwtUtils.getEmail(token)).thenReturn(mail);
        when(restTemplate.exchange(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.any(HttpMethod.class),
                ArgumentMatchers.any(),
                ArgumentMatchers.<Class<User[]>>any()))
                .thenReturn(responseEntity
                );

        UserDetails userDetails = service.loadUserByUsername(token);
        verify(jwtUtils, times(1)).getEmail(token);
        verify(restTemplate, times(1)).exchange(mail, HttpMethod.GET, entity, User[].class);

    }
}