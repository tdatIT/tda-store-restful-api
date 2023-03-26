package com.webapp.tdastore.services;

import com.webapp.tdastore.entities.ProductImage;

import java.util.List;

public interface ProductImageService {
    List<ProductImage> getAllOfProductId(String productId);

    ProductImage findById(long imageId);

    void insertImage(ProductImage productImage);

    void updateImage(ProductImage productImage);
}
