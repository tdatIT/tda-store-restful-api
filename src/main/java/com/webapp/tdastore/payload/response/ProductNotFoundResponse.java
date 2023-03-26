package com.webapp.tdastore.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductNotFoundResponse {
    private int code;
    private String message;
    private long timestamp;
}
