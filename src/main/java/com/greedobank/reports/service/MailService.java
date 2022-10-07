package com.greedobank.reports.service;

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

@Service
public class MailService {
    @Autowired
    private JavaMailSender javaMailSender;
    private final String messageHeader = "Email with attached report";
    private final String messageText = "<h1>Following your request, a report was generated on news that has not been published</h1>";

    public void sendEmailWithAttachment(byte[] bytes) throws MessagingException, IOException {

        MimeMessage msg = javaMailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(msg, true);

        SecurityContextHolder.getContext().getAuthentication();

        String name = SecurityContextHolder.getContext().getAuthentication().getName();

        System.out.println(name);

        helper.setTo(name);

        helper.setSubject(messageHeader);

        helper.setText(messageText, true);

        helper.addAttachment("report.xlsx", new ByteArrayResource(IOUtils.toByteArray(new ByteArrayInputStream(bytes))));

        javaMailSender.send(msg);
    }
}
