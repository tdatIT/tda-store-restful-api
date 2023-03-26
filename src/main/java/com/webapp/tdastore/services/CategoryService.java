package com.webapp.tdastore.services;

import com.webapp.tdastore.entities.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getAll();

    List<Category> getAllPaging(int page);

    Category findById(long id);

    long getTotal();

    void insert(Category category);

    void update(Category category);

}
