package com.greedobank.reports.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class MissedAuthenticationPoint implements AuthenticationEntryPoint {
    @Autowired
    private ObjectMapper mapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException{
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        JsonResponse<String> missedAuthResponse =
                new JsonResponse<>(authException.getMessage());
        mapper.writeValue(response.getOutputStream(), missedAuthResponse);
    }
}
