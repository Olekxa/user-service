package com.greedobank.reports.controller;

import com.RestControllerTestConfig;
import com.greedobank.reports.service.MailService;
import com.greedobank.reports.service.ReportService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration
@WebMvcTest(ReportController.class)
@Import(RestControllerTestConfig.class)
class ReportControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MailService mailService;
    @MockBean
    private ReportService reportService;

    @Test
    @WithMockUser(username = "dzhmur@griddynamics.com", roles = "ADMIN")
    public void shouldReturn200SendingReport() throws Exception {
        Mockito.doNothing().when(mailService).sendEmailWithAttachment(Mockito.any());

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/sendReport")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isOk());

        verify(mailService, times(1)).sendEmailWithAttachment(Mockito.any());
    }

}