package com.greedobank.reports.service;

import com.greedobank.reports.model.News;
import com.greedobank.reports.model.User;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.List;

@Service
public class MailService {
    private final JavaMailSender javaMailSender;
    private final String messageSubject = "Report with unpublished news";
    private final String messageText = "<h1>Following your request, a report was generated on news that has not been published</h1>";
    private final String messageTextDaily = "News for %s";
    private final String title = "<h1> %s </h1>";

    @Autowired
    public MailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendUnpublishedNewsReport(byte[] bytes) throws MessagingException, IOException {
        MimeMessage msg = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
        String name = SecurityContextHolder.getContext().getAuthentication().getName();

        helper.setTo(name);
        helper.setSubject(messageSubject);
        helper.setText(messageText, true);
        helper.addAttachment("report.xlsx", new ByteArrayResource(IOUtils.toByteArray(new ByteArrayInputStream(bytes))));
        javaMailSender.send(msg);
    }

    public void sendDailyNewsReportToUsers(List<News> newsList, User[] listOfUsers) throws MessagingException, IOException {
        MimeMessage msg = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);

        OffsetDateTime dateTime = OffsetDateTime.now().minusHours(24);
        String message = String.format(messageTextDaily, dateTime.toLocalDate().toString());

        sendNewsToAllUsers(newsList, listOfUsers, msg, helper, message);
    }

    private void sendNewsToAllUsers(List<News> newsList, User[] listOfUsers, MimeMessage msg, MimeMessageHelper helper, String message) throws MessagingException {
        for (News news : newsList) {
            helper.setSubject(message);
            String concat = String.format(title, news.getTitle()).concat("\n").concat(news.getDescription());
            helper.setText(concat, true);
            for (User user : listOfUsers) {
                helper.setTo(user.getEmail());
                javaMailSender.send(msg);
            }
        }
    }
}
