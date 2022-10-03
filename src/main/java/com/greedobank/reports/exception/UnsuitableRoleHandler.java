package com.greedobank.reports.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class UnsuitableRoleHandler  implements AccessDeniedHandler {
    @Autowired
    private ObjectMapper mapper;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException{
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        ErrorResponse accessDeniedResponse = new ErrorResponse(accessDeniedException.getMessage());
        mapper.writeValue(response.getOutputStream(), accessDeniedResponse);
    }
}