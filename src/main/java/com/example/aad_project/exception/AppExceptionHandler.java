package com.example.aad_project.exception;

import com.example.aad_project.constant.CommonResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class AppExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {Exception.class})
    public CommonResponse handleServerException(Exception ex, WebRequest webRequest){
        ex.printStackTrace();
        return new CommonResponse(500,"UNEXPECTED_ERROR");
    }

    @ExceptionHandler(value = {CustomException.class})
    public ResponseEntity<CommonResponse> handleCustomException(CustomException ex , WebRequest webRequest){
        ex.printStackTrace();

        return ResponseEntity.ok(new CommonResponse(ex.getStatus(), ex.getMessage()));
    }
}
