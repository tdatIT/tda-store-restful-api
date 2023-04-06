package com.webapp.tdastore.data.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Table(name = "product")
@Entity
@Data
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long productId;

    @Column
    private Long viewCount;
    @Column
    private String productCode;

    @Column
    private String productName;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column
    private int quantity;

    @Column
    private int status;

    @Column
    private double price;

    @Column
    private double promotionPrice;

    @Column
    private Timestamp createDate;

    @Column
    private Timestamp updateDate;

    @Column
    private boolean isDeleted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<ProductType> productType;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<OrderItems> items;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Comment> comments;

    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<ProductImage> images;

    public static final int DISABLE = 1;
    public static final int ENABLE = 0;

    public Product(long productId) {
        this.productId = productId;
    }
}
