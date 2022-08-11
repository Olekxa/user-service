package com.greedobank.reports.exception;

import javax.persistence.EntityNotFoundException;

public class NewsNoFoundException extends EntityNotFoundException {
    public NewsNoFoundException(String message) {
        super(message);
    }
}
