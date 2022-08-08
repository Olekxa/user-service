package com.greedobank.reports.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewsErrorResponse<T> {
    @NotNull
    @NotBlank
    private T cause;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<String> info;
}
