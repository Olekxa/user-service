package com.greedobank.reports.service;

import com.greedobank.reports.client.UserClient;
import com.greedobank.reports.model.News;
import com.greedobank.reports.model.Role;
import com.greedobank.reports.model.RoleTitle;
import com.greedobank.reports.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ScheduledSendingTest {

    @Mock
    private ReportServiceDaily reportServiceDaily;
    @Mock
    private MailService mailService;
    @Mock
    private UserClient userClient;

    private ScheduledSending scheduledSending;

    @BeforeEach
    private void setUp() {
        MockitoAnnotations.openMocks(this);
        new ScheduledSending(reportServiceDaily, mailService, userClient);
    }

    @Test
    public void checkScheduleSend() throws IOException, MessagingException {
        User user = new User(1, "", "", "okukurik@griddynamics.com", new Role(1, RoleTitle.ROLE_ADMIN));
        User[] listUsers = new User[]{user};
        List<News> newsList = new ArrayList();

        when(userClient.getMailsOfUsers()).thenReturn(listUsers);
        when(reportServiceDaily.getExpectedNews()).thenReturn(newsList);
        doNothing().when(mailService).sendDailyNewsReportToUsers(newsList, listUsers);

        mailService.sendDailyNewsReportToUsers(newsList, listUsers);
        verify(mailService, times(1)).sendDailyNewsReportToUsers(newsList, listUsers);
    }
}