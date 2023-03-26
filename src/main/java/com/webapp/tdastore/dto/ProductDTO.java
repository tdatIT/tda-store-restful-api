package com.webapp.tdastore.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.webapp.tdastore.entities.*;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductDTO {

    private long productId;

    private String productCode;

    @NotBlank(message = "Not null")
    @Length(min = 10, max = 500)
    private String productName;

    @Length(min = 25)
    @NotBlank(message = "Not null")
    private String description;

    @NotNull
    private int quantity;

    @NotNull
    private int status;
    @NotNull
    @Min(value = 1000)
    private double price;

   /* @Min(value = 1000)*/
    private double promotionPrice;

    private Timestamp createDate;

    private Timestamp updateDate;

    private Category category;

    private List<ProductType> productType;

    private Set<OrderItems> items;

    private List<Comment> comments;

    private List<ProductImage> images;

    private MultipartFile[] imageFile;


}
