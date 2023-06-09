package com.webapp.tdastore.data.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class OrderItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long itemOId;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;


    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @Column
    private int quantity;

    //store price at create order
    @Column
    private double price;
}
