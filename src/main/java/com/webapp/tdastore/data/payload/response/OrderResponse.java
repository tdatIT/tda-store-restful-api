package com.webapp.tdastore.data.payload.response;

import com.webapp.tdastore.data.entities.Voucher;
import lombok.Data;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Data
public class OrderResponse {
    private long orderId;
    private long userId;
    private double shippingCosts;
    private double discount;
    private double total;
    private int orderStatus;
    private int paymentMethod;
    private Timestamp createDate;
    private String address_detail;
    private long address_id;
    private Voucher voucher;
    private List<OrderItemResp> order_items;

}
