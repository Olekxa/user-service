package com.greedobank.reports.exception;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpServerErrorException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody
    NewsErrorResponse
    handleArgumentNotValid(MethodArgumentNotValidException ex) {
        List<String> error = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        return new NewsErrorResponse(new ErrorResponse("Incorrect request", error));
    }

    @ExceptionHandler(HttpServerErrorException.InternalServerError.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public @ResponseBody
    NewsErrorResponse
    handleServiceFall(HttpServerErrorException.InternalServerError ex) {
        List<String> errors = new ArrayList<>();
        String message = ex.getMessage();
        errors.add(message);
        return new NewsErrorResponse(new ErrorResponse("Unknown error occurred", errors));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody
    NewsErrorResponse
    handleIllegalArgument(IllegalArgumentException ex) {
        List<String> errors = new ArrayList<>();
        String message = ex.getMessage();
        errors.add(message);
        return new NewsErrorResponse(new ErrorResponse("Unknown error occurred", errors));
    }
}

