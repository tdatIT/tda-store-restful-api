package com.webapp.tdastore.controller.web;

import com.webapp.tdastore.data.dto.ProductDTO;
import com.webapp.tdastore.data.entities.Product;
import com.webapp.tdastore.data.exception.CustomExceptionRuntime;
import com.webapp.tdastore.data.payload.response.ProductResponse;
import com.webapp.tdastore.services.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/v1/products")
public class ProductRestController {
    @Autowired
    private ProductService productService;
    @Autowired
    private ModelMapper modelMapper;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<ProductResponse> getList(@RequestParam(required = false) Integer page,
                                         @RequestParam(required = false) Integer size) {
        if (page == null)
            page = 0;
        if (size == null)
            size = 0;
        List<Product> products = productService.findNewProduct(page, size);

        if (products.size() < 1 || size > 12) {
            throw new CustomExceptionRuntime(404, "Not found product with page and number of product or number of product to large");
        } else {
            List<ProductResponse> responseList = products.stream()
                    .map(item ->
                            productService.mappingProductToResponseObject(item))
                    .collect(Collectors.toList());
            return responseList;
        }
    }

    @RequestMapping(value = "/category/{categoryCode}", method = RequestMethod.GET)
    public List<ProductResponse> getProductsByCategoryCode(@PathVariable String categoryCode,
                                                           @RequestParam(required = false) Integer page) {
        if (page == null)
            page = 0;
        List<Product> products = productService.findByCategoryCode(categoryCode, page);
        if (products.size() < 1)
            throw new CustomExceptionRuntime(404, "Not found product with category code");
        List<ProductResponse> responseList = products
                .stream().map(item ->
                        productService.mappingProductToResponseObject(item)
                ).collect(Collectors.toList());
        return responseList;
    }

    @RequestMapping(value = "/{code}", method = RequestMethod.GET)
    public ProductResponse getProductByCode(@PathVariable String code) {
        Product p = productService.findProductByCode(code);
        if (p == null) {
            throw new CustomExceptionRuntime(404, "Not found product have this code");
        } else {
            ProductResponse response = productService.mappingProductToResponseObject(p);
            return response;
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity insertNewProduct(@RequestBody @Valid ProductDTO dto) {
        Product product = modelMapper.map(dto, Product.class);
        String responseCode = productService.insert(product);
        if (responseCode == null) throw new CustomExceptionRuntime(400, "Insert fail !");
        return ResponseEntity.status(HttpStatus.OK).body(responseCode);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public ResponseEntity updateProduct(@RequestBody @Valid ProductDTO dto) {
        if (dto.getProductCode() == null) {
            throw new CustomExceptionRuntime(400, "Not found product");
        }
        Product p = modelMapper.map(dto, Product.class);
        productService.update(p);
        return ResponseEntity.status(HttpStatus.OK).body("Update product success");
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/{code}", method = RequestMethod.DELETE)
    public ResponseEntity updateProduct(@PathVariable String code) {
        Product p = productService.findProductByCode(code);
        if (p == null) {
            throw new CustomExceptionRuntime(400, "Not found product");
        }
        p.setStatus(Product.DISABLE);
        productService.update(p);
        return ResponseEntity.status(HttpStatus.OK).body("Delete product success");
    }
}
