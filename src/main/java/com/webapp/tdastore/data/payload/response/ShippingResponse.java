package com.webapp.tdastore.data.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ShippingResponse {
    private int code;
    private String message;
    private int cost;

}
