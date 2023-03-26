package com.webapp.tdastore.repositories;

import com.webapp.tdastore.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepos extends JpaRepository<Product, Long> {
    @Query("select p from Product p where lower(p.productName) like %:keyword% ")
    List<Product> findByKeyword(@Param("keyword") String keyword);

    List<Product> findByProductCode(String productCode);

    List<Product> findProductByCategoryCode(String code, Pageable pageable);

    List<Product> findAllByOrderByCreateDateDesc(Pageable pageable);

    Product findProductByProductCode(String code);

    @Query("select p from Product p where (:categoryId is null or p.category.categoryId = :categoryId) " +
            "and (:status is null or p.status=:status) and p.isDeleted=false")
    Page<Product> findProductByQuery(@Param("categoryId") Long categoryId,
                                     @Param("status") Integer status, Pageable pageable);
}
