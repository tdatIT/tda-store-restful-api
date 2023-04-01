package com.webapp.tdastore.data.payload;

import lombok.Data;

@Data
public class CartItemRequest {
    private String productCode;
    private int quantity = 1;
}
