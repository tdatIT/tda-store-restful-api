package com.webapp.tdastore.controller.web;

import com.webapp.tdastore.config.ValidatorUtils;
import com.webapp.tdastore.data.entities.Product;
import com.webapp.tdastore.data.exception.CustomExceptionRuntime;
import com.webapp.tdastore.data.payload.response.ProductResponse;
import com.webapp.tdastore.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/search")
public class SearchRestController {
    @Autowired
    private ProductService productService;

    @RequestMapping(value = "/p", method = RequestMethod.GET)
    public List<ProductResponse> getProductsByKeyword(@RequestParam String keyword,
                                                      @RequestParam(required = false) Integer page) {
        if (!keyword.matches(ValidatorUtils.VIETNAMESE_REGEX))
            throw new CustomExceptionRuntime(400, "Request was failed. Please validate input data again");
        if (page == null)
            page = 0;
        List<Product> products = productService.findByKeyword(keyword, page);
        if (products.size() < 1)
            throw new CustomExceptionRuntime(200, "Product with keyword in name was not found ");
        return products.stream()
                .map(t -> productService.mappingProductToResponseObject(t))
                .collect(Collectors.toList());
    }
}
