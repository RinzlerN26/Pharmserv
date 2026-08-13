package com.pharma.pharmserv.Exception;

import org.springframework.http.HttpStatus;

public class CustomServiceException extends RuntimeException {
    private final HttpStatus status;

    public CustomServiceException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
