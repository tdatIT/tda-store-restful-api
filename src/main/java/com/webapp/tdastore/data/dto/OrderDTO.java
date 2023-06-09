package com.webapp.tdastore.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO implements Serializable {

    @Positive
    private long userId;
    @NonNull
    @Positive
    private double shippingCosts;
    @NonNull
    @Positive
    private double discount;
    @NonNull
    @Positive
    private double total;
    @NonNull
    @Positive
    private int paymentMethod;
    @NonNull
    @Positive
    private long address_id;
    @Positive
    private String voucher_code;
    @NotEmpty
    private List<Long> cart_items;
}
