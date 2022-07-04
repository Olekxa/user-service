package com.greedobank.reports.exeptions;

import lombok.Getter;
import lombok.AllArgsConstructor;

@Getter
@AllArgsConstructor
public class NewsException extends RuntimeException{
    private final String exception;
}
