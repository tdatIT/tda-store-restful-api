package com.webapp.tdastore.dto;

import com.webapp.tdastore.entities.Product;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Data
public class ProductTypeDTO {
    private long noType;

    @NotBlank
    private String productCode;

    @NotNull
    private int type;

    @NotBlank
    private String description;

    private Timestamp updateTime;

    private Double specialPrice;

    private boolean isDeleted;

    private Product product;
}
