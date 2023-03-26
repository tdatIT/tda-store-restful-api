package com.webapp.tdastore.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CartItems {
    private Product product;
    private int quantity;

    public double getAmount() {
        if (product.getPromotionPrice() > 0) {
            return quantity * product.getPromotionPrice();
        }
        return quantity * product.getPrice();
    }
}
