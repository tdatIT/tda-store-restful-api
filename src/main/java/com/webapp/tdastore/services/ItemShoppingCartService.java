package com.webapp.tdastore.services;

import com.webapp.tdastore.entities.ItemShoppingCart;

import java.util.List;

public interface ItemShoppingCartService {
    List<ItemShoppingCart> findAllByUserId(long userId);

    double totalAmountHasDiscount(long userId);

    double totalPriceForItem(long userId);

    int size(long userId);

    void insert(String productCode, int quantity, long userId);

    void remove(long itemId);

    void updateQuantity(long userId, String productCode, int quantity);

    void clear(long userId);

}
