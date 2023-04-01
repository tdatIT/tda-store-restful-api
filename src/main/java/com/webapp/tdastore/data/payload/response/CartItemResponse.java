package com.webapp.tdastore.data.payload.response;

import com.webapp.tdastore.data.entities.Product;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class CartItemResponse {
    private long itemId;
    private int quantity;
    private double amount;
    //product
    private String productCode;
    private String productName;
    private int status;
    private double price;
    private double promotionPrice;
    private long categoryId;
    private List<String> images_file;

    public void setProductInfo(Product p) {
        this.productCode = p.getProductCode();
        this.productName = p.getProductName();
        this.status = p.getStatus();
        this.price = p.getPrice();
        this.promotionPrice = p.getPromotionPrice();
        this.categoryId = p.getCategory().getCategoryId();
        images_file = p.getImages().stream().map(t -> t.getUrlImage()).collect(Collectors.toList());
    }


}
