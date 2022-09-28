package com.greedobank.reports.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {
    @Autowired
    private Environment environment;

    @GetMapping("/active-profiles")
    @ResponseBody
    public String[] getActiveProfiles() {
        return environment.getActiveProfiles();
    }
}
