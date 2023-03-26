package com.webapp.tdastore.services.impl;

import com.webapp.tdastore.entities.ProductImage;
import com.webapp.tdastore.repositories.ProductImageRepos;
import com.webapp.tdastore.services.ProductImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductImageServiceImpl implements ProductImageService {
    @Autowired
    ProductImageRepos imageRepos;

    @Override
    public List<ProductImage> getAllOfProductId(String productId) {
        return imageRepos.findProductImageByProductId(productId);
    }

    @Override
    public ProductImage findById(long imageId) {
        return imageRepos.findById(imageId).orElseThrow();
    }

    @Override
    public void insertImage(ProductImage productImage) {
        imageRepos.save(productImage);
    }

    @Override
    public void updateImage(ProductImage productImage) {
        imageRepos.save(productImage);
    }
}
