package com.greedobank.reports.service;

import com.greedobank.reports.model.Role;
import com.greedobank.reports.model.RoleTitle;
import com.greedobank.reports.model.User;
import com.greedobank.reports.model.UserWrapper;
import com.greedobank.reports.utils.JwtUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserDetailsServiceImplTest {
    private UserDetailsServiceImpl service;

    @Mock
    private UserClient userClient;

    @Mock
    private JwtUtils jwtUtils;

    @BeforeEach
    private void setUp() {
        MockitoAnnotations.openMocks(this);
        service = new UserDetailsServiceImpl(userClient, jwtUtils);
    }

    @Test
    public void shouldReturnUserWrapper() {
        String token = "";
        String mail = "dzhmur@griddynamics.com";
        User user = new User(1, "", "", mail, new Role(1, RoleTitle.ROLE_ADMIN));
        User[] array = {user};

        when(jwtUtils.getEmail(token)).thenReturn(mail);
        when(userClient.buildRequest(mail)).thenReturn(array);

        UserDetails userDetails = service.loadUserByUsername(token);
        verify(jwtUtils, times(1)).getEmail(token);
        verify(userClient, times(1)).buildRequest(mail);
        assertEquals(new UserWrapper(user), userDetails);
    }

    @Test
    public void shouldThrowException() {
        String token = "";
        String mail = "dzhmur@griddynamics.com";

        when(jwtUtils.getEmail(token)).thenReturn(mail);
        when(userClient.buildRequest(mail)).thenThrow(new UsernameNotFoundException("User not found with such email"));

        assertThrows(UsernameNotFoundException.class, () -> service.loadUserByUsername(token));
    }
}