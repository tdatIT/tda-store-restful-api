package com.webapp.tdastore.services.impl;

import com.webapp.tdastore.data.entities.ItemShoppingCart;
import com.webapp.tdastore.data.entities.Product;
import com.webapp.tdastore.data.entities.User;
import com.webapp.tdastore.data.repositories.ItemShoppingCartRepos;
import com.webapp.tdastore.data.repositories.ProductRepos;
import com.webapp.tdastore.data.repositories.UserRepos;
import com.webapp.tdastore.services.ItemShoppingCartService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
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
    public List<ItemShoppingCart> findAllByItemsId(List<Long> itemId) {
        return itemId.stream().map(t ->
                cartRepos.findById(t).orElseThrow()
        ).collect(Collectors.toList());
    }

    @Override
    public double totalAmountSelectItem(List<ItemShoppingCart> items) {
        try {
            return items.stream().mapToDouble(
                    t -> t.getProduct().getPrice() * t.getQuantity()).sum();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return 0;
    }

    @Override
    public double totalHaveDiscountSelectItem(List<ItemShoppingCart> items) {
        try {
            return items.stream().mapToDouble(
                    t -> (t.getProduct().getPromotionPrice() > 0 ?
                            t.getProduct().getPromotionPrice() : t.getProduct().getPromotionPrice()) * t.getQuantity()).sum();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return 0;
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
    public void removeItemWhenOrderItemWasCreated(long itemId) {
        ItemShoppingCart item = cartRepos.findById(itemId).orElseThrow();
        cartRepos.delete(item);
    }

    @Override
    public void remove(long userId, String productCode) {
        ItemShoppingCart item = cartRepos.findItemShoppingCartByProductCodeAndUserId(productCode, userId);
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
