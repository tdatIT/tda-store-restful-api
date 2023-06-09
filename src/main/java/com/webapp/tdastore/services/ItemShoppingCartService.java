package com.webapp.tdastore.services;

import com.webapp.tdastore.data.entities.ItemShoppingCart;

import java.util.List;

public interface ItemShoppingCartService {
    List<ItemShoppingCart> findAllByUserId(long userId);

    List<ItemShoppingCart> findAllByItemsId(List<Long> itemId);

    ItemShoppingCart findItemById(long itemId);

    double totalAmountSelectItem(List<ItemShoppingCart> items);

    double totalHaveDiscountSelectItem(List<ItemShoppingCart> items);

    double totalAmountHasDiscount(long userId);

    double totalPriceForItem(long userId);

    int size(long userId);

    void insert(String productCode, int quantity, long userId);

    void remove(long userId, String productCode);

    void updateQuantity(long userId, String productCode, int quantity);

    void removeItemWhenOrderItemWasCreated(long itemId);

    void clear(long userId);

}
