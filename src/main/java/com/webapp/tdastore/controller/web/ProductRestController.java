package com.webapp.tdastore.controller.web;

import com.webapp.tdastore.dto.ProductDTO;
import com.webapp.tdastore.entities.Product;
import com.webapp.tdastore.exception.ProductNotFoundException;
import com.webapp.tdastore.payload.response.ProductResponse;
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
    public List<ProductResponse> getList(@RequestParam int page, @RequestParam int num) {
        List<Product> products = productService.findNewProduct(page, num);
        if (products.size() < 1 || num > 12) {
            throw new ProductNotFoundException("Not found product with page and number of product or number of product to large");
        } else {
            List<ProductResponse> responseList = products.stream().map(item -> {
                ProductResponse response = modelMapper.map(item, ProductResponse.class);
                response.setImages_file(
                        item.getImages()
                                .stream()
                                .map(t -> t.getUrlImage())
                                .collect(Collectors.toList()));
                response.setCategoryId(item.getCategory().getCategoryId());
                return response;
            }).collect(Collectors.toList());
            return responseList;
        }
    }

    @RequestMapping(value = "/{code}", method = RequestMethod.GET)
    public ProductResponse getProductByCode(@PathVariable String code) {
        Product p = productService.findProductByCode(code);
        if (p == null) {
            throw new ProductNotFoundException("Not found product have this code");
        } else {
            ProductResponse response = modelMapper.map(p, ProductResponse.class);
            response.setImages_file(
                    p.getImages()
                            .stream()
                            .map(t -> t.getUrlImage())
                            .collect(Collectors.toList()));
            response.setCategoryId(p.getCategory().getCategoryId());
            return response;
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity insertNewProduct(@RequestBody @Valid ProductDTO dto) {
        Product product = modelMapper.map(dto, Product.class);
        String responseCode = productService.insert(product);
        if (responseCode == null) throw new ProductNotFoundException("Insert fail !");
        return ResponseEntity.status(HttpStatus.OK).body(responseCode);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public ResponseEntity updateProduct(@RequestBody @Valid ProductDTO dto) {
        if (dto.getProductCode() == null) {
            throw new ProductNotFoundException("Not found product");
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
            throw new ProductNotFoundException("Not found product");
        }
        p.setStatus(Product.DISABLE);
        productService.update(p);
        return ResponseEntity.status(HttpStatus.OK).body("Delete product success");
    }
}
