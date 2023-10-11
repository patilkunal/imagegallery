package com.inovisionsoftware.imagegallery;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;

@Component
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    @ExceptionHandler(value = {IOException.class})
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String responseBody = "IO Error in performing request: " + ex.getMessage();
        return super.handleExceptionInternal(ex, body, headers, status, request);
    }
}
