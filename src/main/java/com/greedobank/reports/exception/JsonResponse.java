package com.greedobank.reports.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Map;
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class JsonResponse <T> {
    @NonNull
    private T content;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Map<String, String[]> errors;
}
