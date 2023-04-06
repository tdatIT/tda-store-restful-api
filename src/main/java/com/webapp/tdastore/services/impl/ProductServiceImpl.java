package com.webapp.tdastore.services.impl;

import com.webapp.tdastore.data.entities.Product;
import com.webapp.tdastore.data.entities.generator.GeneratorCode;
import com.webapp.tdastore.data.payload.response.ProductResponse;
import com.webapp.tdastore.data.repositories.ProductRepos;
import com.webapp.tdastore.services.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    ProductRepos productRepos;

    @Autowired
    GeneratorCode generatorCode;

    @Override
    public ProductResponse mappingProductToResponseObject(Product product) {
        ProductResponse response = modelMapper.map(product, ProductResponse.class);
        response.setImages_file(product.getImages().stream().map(
                t -> t.getUrlImage()
        ).collect(Collectors.toList()));
        response.setCategoryId(product.getCategory().getCategoryId());
        return response;
    }

    @Override
    public List<Product> findAll(int page, int number) {
        return productRepos.findAll(PageRequest.of(page, number)).getContent();
    }

    @Override
    public List<Product> findNewProduct(int page, int number) {
        return productRepos.findAllByOrderByCreateDateDesc(PageRequest.of(page, number));
    }

    @Override
    public List<Product> findPopularProduct(int page) {
        return productRepos.findPopularProduct(PageRequest.of(page, 6));
    }

    @Override
    public List<Product> findBestSeller(int page) {
        return productRepos.findBestSellingProducts(PageRequest.of(page, 6));
    }

    @Override
    public Product findProductByCode(String code) {
        return productRepos.findProductByProductCode(code);
    }

    @Override
    public List<Product> findByKeyword(String keyword, Integer page) {
        int size_product = 10;
        return productRepos.findByKeyword(keyword, PageRequest.of(page, size_product));
    }

    @Override
    public Product findById(long id) {
        return productRepos.findById(id).orElseThrow();
    }

    @Override
    public long getCountProduct() {
        return productRepos.count();
    }

    @Transactional
    @Override
    public void increaseViewCount(Product product) {
        long count = product.getViewCount() + 1;
        product.setViewCount(count);
        productRepos.save(product);
    }

    @Override
    @Transactional
    public String insert(Product product) {
        product.setCreateDate(new Timestamp(new Date().getTime()));
        Product p = productRepos.save(product);
        p.setViewCount(0L);
        //Generate product code
        p.setProductCode(generatorCode.generator(p.getProductId() + ""));
        productRepos.save(p);
        return p.getProductCode();
    }

    @Override
    @Transactional
    public void update(Product product) {
        Product old = this.findProductByCode(product.getProductCode());
        product.setCreateDate(old.getCreateDate());
        if (product.getQuantity() == 0) {
            product.setStatus(2);
        }
        product.setUpdateDate(new Timestamp(new Date().getTime()));
        productRepos.save(product);
    }

    @Override
    @Transactional
    public void disableProduct(Product product) {
        product.setDeleted(true);
        product.setStatus(-1);
        product.setUpdateDate(new Timestamp(new Date().getTime()));
        productRepos.save(product);
    }

    @Override
    public List<Product> findByCategoryCode(String code, int page) {
        return productRepos.findProductByCategoryCode(code, PageRequest.of(page, 9));
    }
}
