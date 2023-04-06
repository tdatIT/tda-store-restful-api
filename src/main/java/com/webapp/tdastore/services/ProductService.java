package com.webapp.tdastore.services;

import com.webapp.tdastore.data.entities.Product;
import com.webapp.tdastore.data.payload.response.ProductResponse;

import java.util.List;

public interface ProductService {
    List<Product> findAll(int page, int number);

    List<Product> findNewProduct(int page, int number);

    List<Product> findPopularProduct(int page);

    List<Product> findBestSeller(int page);

    Product findProductByCode(String code);

    List<Product> findByKeyword(String keyword, Integer page);

    Product findById(long id);

    long getCountProduct();

    String insert(Product product);

    void update(Product product);

    void increaseViewCount(Product product);

    void disableProduct(Product product);

    List<Product> findByCategoryCode(String code, int page);

    ProductResponse mappingProductToResponseObject(Product products);
}
