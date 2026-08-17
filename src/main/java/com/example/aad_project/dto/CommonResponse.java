package com.example.aad_project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonResponse {

    private int status;
    private Object body;
    private String message;

    public CommonResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
