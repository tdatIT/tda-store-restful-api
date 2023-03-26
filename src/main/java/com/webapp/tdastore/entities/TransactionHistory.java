package com.webapp.tdastore.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
public class TransactionHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long noLog;

    @Column
    private double value;

    @Column
    private String description;

    @Column
    private Timestamp createDate;

    @Column
    private String tokenOrOrderId;

    public static final String ORDER_SUCCESS = "order_success";
    public static final String PAYPAL_PAYMENT = "paypal_payment";
    public static final String VISA_PAYMENT = "visa_payment";
    public static final String ROLL_BACK = "roll_back";
}
