package com.webapp.tdastore.exception;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

@AllArgsConstructor
@NoArgsConstructor
public class OrderException extends RuntimeException {
    private String message;

    @ExceptionHandler(OrderException.class)
    public ResponseEntity responseFailOrder(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new OrderException("Insert Product Fail"));
    }
}
