package com.webapp.tdastore.controller.exception;

import com.webapp.tdastore.exception.ProductNotFoundException;
import com.webapp.tdastore.payload.response.ProductNotFoundResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ProductRestException {
    @ExceptionHandler
    public ResponseEntity<ProductNotFoundResponse> catchNotFoundProduct(ProductNotFoundException ex) {
        ProductNotFoundResponse response =
                new ProductNotFoundResponse(400, ex.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ProductNotFoundResponse> catchErrorProduct(Exception e) {
        ProductNotFoundResponse response =
                new ProductNotFoundResponse(400, e.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
