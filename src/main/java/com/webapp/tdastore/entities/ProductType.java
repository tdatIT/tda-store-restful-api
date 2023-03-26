package com.webapp.tdastore.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Table(name = "product_type")
@Entity
@Getter
@Setter
@EqualsAndHashCode
public class ProductType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long noType;

    @Column
    private int type;

    @Column
    private String description;

    @Column
    private Timestamp updateTime;

    @Column
    private double specialPrice;

    @Column
    private boolean isDeleted;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonIgnore
    private Product product;
}
