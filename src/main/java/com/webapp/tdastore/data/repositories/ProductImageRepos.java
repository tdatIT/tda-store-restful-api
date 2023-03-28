package com.webapp.tdastore.data.repositories;

import com.webapp.tdastore.data.entities.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductImageRepos extends JpaRepository<ProductImage, Long> {
    @Query("select i from ProductImage i where  i.product.productId=?1")
    List<ProductImage> findProductImageByProductId(String productId);
}
