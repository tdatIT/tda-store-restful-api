package com.webapp.tdastore.services.impl;

import com.webapp.tdastore.data.entities.Category;
import com.webapp.tdastore.data.repositories.CategoryRepos;
import com.webapp.tdastore.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryRepos categoryRepos;

    @Override
    public List<Category> getAll() {
        return categoryRepos.findAll();
    }

    @Override
    public List<Category> getAllPaging(int page) {
        //show 5 category and cast to list type
        return categoryRepos.findAll(PageRequest.of(page, 5)).getContent();
    }

    @Override
    public long getTotal() {
        return categoryRepos.count();
    }

    @Override
    public Category findById(long id) {
        return categoryRepos.findById(id).orElseThrow();
    }

    @Override
    @Transactional
    public void insert(Category category) {
        category.setCreateDate(new Timestamp(new Date().getTime()));
        category.setUpdateDate(new Timestamp(new Date().getTime()));
        category.setStatus(true);
        categoryRepos.save(category);
    }

    @Override
    @Transactional
    public void update(Category category) {
        category.setUpdateDate(new Timestamp(new Date().getTime()));
        categoryRepos.save(category);
    }
}
