package com.webapp.tdastore.services.impl;

import com.webapp.tdastore.entities.ProductType;
import com.webapp.tdastore.repositories.ProductTypeRepos;
import com.webapp.tdastore.services.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;

@Service
public class ProductTypeServiceImpl implements ProductTypeService {
    @Autowired
    private ProductTypeRepos typeRepos;

    @Override
    public ProductType findById(long id) {
        return typeRepos.findById(id).orElseThrow();
    }

    @Override
    public void insert(ProductType type) {
        type.setUpdateTime(new Timestamp(new Date().getTime()));
        typeRepos.save(type);
    }

    @Override
    public void update(ProductType type) {
        type.setUpdateTime(new Timestamp(new Date().getTime()));
        typeRepos.save(type);
    }

    @Override
    public void delete(ProductType type) {
        type.setDeleted(true);
        type.setUpdateTime(new Timestamp(new Date().getTime()));
        typeRepos.save(type);
    }
}
