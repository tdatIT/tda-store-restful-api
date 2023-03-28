package com.webapp.tdastore.controller.exception;

import com.webapp.tdastore.data.exception.CustomExceptionRuntime;
import com.webapp.tdastore.data.payload.response.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestApiException {
    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> catchExceptionResponse(CustomExceptionRuntime ex) {
        ExceptionResponse response =
                new ExceptionResponse(ex.getCode(), ex.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


}
