package com.webapp.tdastore.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class ItemShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long itemId;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public double getAmount() {
        return quantity * (product.getPromotionPrice() > 0 ? product.getPromotionPrice() : product.getPrice());
    }
}
