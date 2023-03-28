package com.webapp.tdastore.data.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
public class CustomResponse {
    private int code;
    private String message;
    private long timestamp;
}
