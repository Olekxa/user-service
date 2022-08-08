package com.greedobank.reports.exception;

import com.greedobank.reports.dto.NewsRequestDTO;

public class NewsIncorrectRequestException extends RuntimeException {

    public NewsIncorrectRequestException(NewsRequestDTO request) {
        super("Can't create news, bad request");
    }
}
