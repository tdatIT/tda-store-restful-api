package com.webapp.tdastore.services.impl;

import com.webapp.tdastore.entities.Product;
import com.webapp.tdastore.entities.generator.GeneratorCode;
import com.webapp.tdastore.repositories.ProductRepos;
import com.webapp.tdastore.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepos productRepos;

    @Autowired
    GeneratorCode generatorCode;

    @Override
    public List<Product> findAll(int page, int number) {
        return productRepos.findAll(PageRequest.of(page, number)).getContent();
    }

    @Override
    public List<Product> findNewProduct(int page, int number) {
        return productRepos.findAllByOrderByCreateDateDesc(PageRequest.of(page, number));
    }

    @Override
    public List<Product> findHotProduct(int page, int number) {
        return productRepos.findAllByOrderByCreateDateDesc(PageRequest.of(page, number));
    }

    @Override
    public List<Product> findHotTrend(int page, int number) {
        return productRepos.findAllByOrderByCreateDateDesc(PageRequest.of(page, number));
    }

    @Override
    public List<Product> listProductByCode(String code) {
        return productRepos.findByProductCode(code);
    }

    @Override
    public Product findProductByCode(String code) {
        return productRepos.findProductByProductCode(code);
    }

    @Override
    public List<Product> findQuery(Long categoryId, Integer status, int page, int number) {
        return productRepos.findProductByQuery(
                categoryId,
                status,
                PageRequest.of(page, number)
        ).getContent();
    }

    @Override
    public List<Product> findByKeyword(String keyword) {
        return productRepos.findByKeyword(keyword);
    }

    @Override
    public Product findById(long id) {
        return productRepos.findById(id).orElseThrow();
    }

    @Override
    public long getCountProduct() {
        return productRepos.count();
    }

    @Override
    @Transactional
    public String insert(Product product) {
        product.setCreateDate(new Timestamp(new Date().getTime()));
        Product p = productRepos.save(product);
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
    public List<Product> findByCategoryCode(String code,int page) {
        return productRepos.findProductByCategoryCode(code, PageRequest.of(page, 9));
    }
}
