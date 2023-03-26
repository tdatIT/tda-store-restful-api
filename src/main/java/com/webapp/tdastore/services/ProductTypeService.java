package com.webapp.tdastore.services;

import com.webapp.tdastore.entities.ProductType;

public interface ProductTypeService {

    ProductType findById(long id);

    void insert(ProductType type);

    void update(ProductType type);

    void delete(ProductType type);
}
