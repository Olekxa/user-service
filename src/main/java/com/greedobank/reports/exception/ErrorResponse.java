package com.greedobank.reports.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ErrorResponse {
    @NotNull
    private String reason;
    @NotNull
    private String details;
}
