package com.greedobank.reports.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
public class JsonResponse <T> {
    @NonNull
    private T content;
    @JsonInclude(JsonInclude.Include.NON_NULL) private Map<String, String[]> errors;
}