package com.pharma.pharmserv.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.pharma.pharmserv.DTO.Response.ErrorResponse;

import java.time.LocalDateTime;
import java.util.Objects;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGenericException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ErrorResponse(
                        LocalDateTime.now(),
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        ex.getMessage()));
    }

    @ExceptionHandler(CustomServiceException.class)
    public ResponseEntity<?> handleCustomException(CustomServiceException ex) {
        HttpStatus status = ex.getStatus() != null ? ex.getStatus() : HttpStatus.INTERNAL_SERVER_ERROR;

        return ResponseEntity.status(Objects.requireNonNull(status)).body(
                new ErrorResponse(
                        LocalDateTime.now(),
                        status.value(),
                        ex.getMessage()));
    }
}