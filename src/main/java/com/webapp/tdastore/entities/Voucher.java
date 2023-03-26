package com.webapp.tdastore.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;

@Table(name = "voucher")
@Entity
@Getter
@Setter
public class Voucher {
    @Id
    private String voucherCode;

    @Column
    private int type;

    @Column
    private double discount;

    @Column
    private Date startDate;

    @Column
    private Date endDate;

    @Column
    private int quantityAvailable;
    @Column
    private boolean isDeleted;
}
