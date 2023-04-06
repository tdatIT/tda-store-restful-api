package com.webapp.tdastore.data.payload.response;

import com.webapp.tdastore.data.entities.ProductType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {
    private long productId;
    private String productCode;
    private String productName;
    private String description;
    private int quantity;
    private int status;
    private double price;
    private double promotionPrice;
    private long categoryId;
    private Timestamp createDate;
    private Timestamp updateDate;
    private boolean isDeleted;
    private Long viewCount;

    private List<ProductType> productType;
    private List<String> images_file;


}
