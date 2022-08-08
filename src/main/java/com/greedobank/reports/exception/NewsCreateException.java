package com.greedobank.reports.exception;

import com.greedobank.reports.dto.NewsRequestDTO;

public class NewsCreateException extends RuntimeException {

    public NewsCreateException(NewsRequestDTO request) {
        super("Can't create news, bad request");
    }
}
