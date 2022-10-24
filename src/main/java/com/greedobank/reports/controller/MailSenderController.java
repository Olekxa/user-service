package com.greedobank.reports.controller;

import com.greedobank.reports.service.MailService;
import com.greedobank.reports.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.io.IOException;

@RestController
@Validated
public class MailSenderController {
    private final ReportService reportService;
    private final MailService mailService;

    @Autowired
    public MailSenderController(ReportService reportService, MailService mailService) {
        this.reportService = reportService;
        this.mailService = mailService;
    }

    @PostMapping("/api/v1/expected-reports")
    @ResponseBody
    @Operation(summary = "Create report", description = "Create report")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void sendReport() throws IOException, MessagingException {
        mailService.sendEmailWithAttachReportWithUnpublishedNews(reportService.generateXlsxReport());
    }
}
