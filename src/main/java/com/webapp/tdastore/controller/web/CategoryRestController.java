package com.webapp.tdastore.controller.web;

import com.webapp.tdastore.data.entities.Category;
import com.webapp.tdastore.data.exception.CustomExceptionRuntime;
import com.webapp.tdastore.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/categories")
public class CategoryRestController {
    @Autowired
    private CategoryService categoryService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Category> getCategoryList() {
        List<Category> categories = categoryService.getAll();
        if (categories.size() < 0) {
            throw new CustomExceptionRuntime(400,"Get categories was failed");
        }
        return categories;
    }

}
