package com.example.aad_project.exception;

import com.example.aad_project.constant.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@Slf4j
@ControllerAdvice
public class AppExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<CommonResponse> handleCustomException(
            CustomException ex,
            WebRequest webRequest
    ) {
        log.error("CustomException: {}", ex.getMessage());
        return ResponseEntity.ok(new CommonResponse(ex.getStatus(), ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CommonResponse> handleValidationException(
            MethodArgumentNotValidException ex,
            WebRequest webRequest
    ) {
        String message = ex.getBindingResult().getFieldErrors().stream()
                .findFirst()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .orElse("Validation failed");

        log.error("Validation error: {}", message);
        return ResponseEntity.ok(new CommonResponse(400, message));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommonResponse> handleServerException(
            Exception ex,
            WebRequest webRequest
    ) {
        log.error("Unexpected error", ex);
        return ResponseEntity.ok(
                new CommonResponse(500, "UNEXPECTED_ERROR")
        );
    }
}