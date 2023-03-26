package com.webapp.tdastore.services;

import com.webapp.tdastore.entities.Product;

import java.util.List;

public interface ProductService {
    List<Product> findAll(int page, int number);

    List<Product> findNewProduct(int page, int number);

    List<Product> findHotProduct(int page, int number);

    List<Product> findHotTrend(int page, int number);


    List<Product> findQuery(Long categoryId, Integer status, int page, int number);

    List<Product> listProductByCode(String code);

    Product findProductByCode(String code);

    List<Product> findByKeyword(String keyword);

    Product findById(long id);

    long getCountProduct();

    String insert(Product product);

    void update(Product product);

    void disableProduct(Product product);

    List<Product> findByCategoryCode(String code,int page);
}
