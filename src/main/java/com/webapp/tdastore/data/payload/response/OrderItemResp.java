package com.webapp.tdastore.data.payload.response;

import lombok.Data;

@Data
public class OrderItemResp {
    private long itemOId;
    private long productId;
    private String productCode;
    private String productName;
    private String productImg;
    private int quantity;
    private double price;
}
