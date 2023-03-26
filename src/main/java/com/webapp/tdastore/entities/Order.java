package com.webapp.tdastore.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Table(name = "orders")
@Entity
@Getter
@Setter
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long orderId;

    @Column
    private double shippingCosts;

    @Column
    private double discount;

    @Column
    private double total;

    @Column
    private int orderStatus;

    @Column
    private int paymentMethod;

    @Column
    private Timestamp createDate;

    @Column
    private Timestamp updateDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "address_id")
    private UserAddress address;

    @ManyToOne
    @JoinColumn(name = "voucher_code")
    private Voucher voucher;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItems> items = new ArrayList<>();


    //const for payment method
    public static final int COD_PAYMENT = 1;
    public static final int VISA_PAYMENT = 2;
    public static final int PAYPAL_PAYMENT = 3;
    //const for order status
    public static final int PENDING_STATUS = 1;
    public static final int PROCESSING_STATUS = 2;
    public static final int COMPLETE_STATUS = 3;
    public static final int CANCEL_STATUS = 4;
    public static final int CHECK_STATUS = 0;

}
