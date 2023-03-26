package com.webapp.tdastore.services.impl;

import com.webapp.tdastore.entities.ItemShoppingCart;
import com.webapp.tdastore.entities.Product;
import com.webapp.tdastore.entities.User;
import com.webapp.tdastore.repositories.ItemShoppingCartRepos;
import com.webapp.tdastore.repositories.ProductRepos;
import com.webapp.tdastore.repositories.UserRepos;
import com.webapp.tdastore.services.ItemShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemShoppingCartServiceImpl implements ItemShoppingCartService {
    @Autowired
    private ProductRepos productRepos;
    @Autowired
    private UserRepos userRepos;
    @Autowired
    private ItemShoppingCartRepos cartRepos;

    @Override
    public List<ItemShoppingCart> findAllByUserId(long userId) {
        return cartRepos.findAllByUserId(userId);
    }

    @Override
    public double totalAmountHasDiscount(long userId) {
        User us = userRepos.findById(userId).orElseThrow();
        if (us != null) {
            return findAllByUserId(userId).stream().mapToDouble(
                    t -> (t.getProduct().getPromotionPrice() > 0 ?
                            t.getProduct().getPromotionPrice() : t.getProduct().getPromotionPrice()) * t.getQuantity()).sum();
        }
        return 0;
    }

    @Override
    public double totalPriceForItem(long userId) {
        User us = userRepos.findById(userId).orElseThrow();
        if (us != null) {
            return findAllByUserId(userId).stream().mapToDouble(
                    t -> t.getProduct().getPrice() * t.getQuantity()).sum();
        }
        return 0;
    }

    @Override
    public int size(long userId) {
        User us = userRepos.findById(userId).orElseThrow();
        if (us != null) {
            return findAllByUserId(userId).stream().mapToInt(
                    t -> t.getQuantity()).sum();
        }
        return 0;
    }

    @Override
    public void insert(String productCode, int quantity, long userId) {
        Product p = productRepos.findProductByProductCode(productCode);
        User us = userRepos.findById(userId).orElseThrow();
        if (p != null && us != null) {
            ItemShoppingCart item = cartRepos
                    .findItemShoppingCartByProductCodeAndUserId(productCode, userId);
            if (item != null) {
                item.setQuantity(item.getQuantity() + quantity);
            } else {
                item = new ItemShoppingCart();
                item.setUser(us);
                item.setQuantity(quantity);
                item.setProduct(p);
            }
            cartRepos.save(item);
        }
    }

    @Override
    public void remove(long itemId) {
        ItemShoppingCart item = cartRepos.findById(itemId).orElseThrow();
        if (item != null) {
            cartRepos.delete(item);
        }
    }

    @Override
    public void updateQuantity(long userId, String productCode, int quantity) {
        ItemShoppingCart item = cartRepos.findItemShoppingCartByProductCodeAndUserId(productCode, userId);
        if (item != null) {
            if (quantity > 0) {
                item.setQuantity(quantity);
                cartRepos.save(item);
            } else
                cartRepos.delete(item);
        }
    }

    @Override
    public void clear(long userId) {
        List<ItemShoppingCart> item = findAllByUserId(userId);
        item.stream().forEach(t -> {
            cartRepos.delete(t);
        });
    }
}
