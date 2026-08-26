package com.example.aad_project.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

    private final int status;

    public CustomException(String message) {
        super(message);
        this.status = 400;

    }

    public CustomException(int status, String message) {
        super(message);
        this.status = status;
    }
}
