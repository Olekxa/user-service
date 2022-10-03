package com.greedobank.reports.service;

import com.greedobank.reports.client.UserClient;
import com.greedobank.reports.model.Role;
import com.greedobank.reports.model.RoleTitle;
import com.greedobank.reports.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserClientTest {
    @InjectMocks
    private UserClient userClient;
    @Mock
    private RestTemplate restTemplate;


    @BeforeEach
    private void setUp() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(userClient, "url", "http://localhost:8082/api/v1/users?email=");
    }

    @Test
    public void shouldReturnUser() {
        String mail = "dzhmur@griddynamics.com";
        String request = "http://localhost:8082/api/v1/users?email=dzhmur@griddynamics.com";
        User user = new User(1, "", "", mail, new Role(1, RoleTitle.ROLE_ADMIN));
        User[] array = {user};
        ResponseEntity<User[]> responseEntity = new ResponseEntity<>(array, HttpStatus.ACCEPTED);
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(headers);

        when(restTemplate.exchange(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.any(HttpMethod.class),
                ArgumentMatchers.any(),
                ArgumentMatchers.<Class<User[]>>any()))
                .thenReturn(responseEntity);

        User[] userByEmail = userClient.getUserByEmail(mail);
        verify(restTemplate, times(1)).exchange(request, HttpMethod.GET, entity, User[].class);
        assertEquals(array, userByEmail);
    }
}