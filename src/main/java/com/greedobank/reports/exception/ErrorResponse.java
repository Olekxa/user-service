package com.greedobank.reports.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {
    @NotNull
    private String reason;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<String> details;

    public ErrorResponse(String reason) {
        this.reason = reason;
    }
}

