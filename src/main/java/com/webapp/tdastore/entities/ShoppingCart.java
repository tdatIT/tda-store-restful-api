package com.webapp.tdastore.entities;


import com.webapp.tdastore.repositories.ItemShoppingCartRepos;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
@SessionScope
@Getter
@Setter
@NoArgsConstructor
public class ShoppingCart {

    @Autowired
    private ItemShoppingCartRepos cartRepos;

    static final Logger logger = LoggerFactory.getLogger("ShoppingCart");

    private Map<String, CartItems> cart = new HashMap<String, CartItems>();

    public Collection<CartItems> getCartItems() {
        return cart.values();
    }

    public void addToCart(Product product, int quantity) {
        CartItems existItem = cart.get(product.getProductCode());
        if (existItem != null) {
            existItem.setQuantity(existItem.getQuantity() + quantity);
        } else
            cart.put(product.getProductCode(), new CartItems(product, quantity));
    }

    public void updateToCart(String code, int quantity) {
        CartItems existItem = cart.get(code);
        existItem.setQuantity(quantity);
        if (existItem.getQuantity() <= 0) {
            cart.remove(code);
        }
    }

    public int getSizeCart() {
        return cart.values().stream().mapToInt(t -> t.getQuantity()).sum();
    }

    public double getTotalPrice() {
        return cart.values().stream()
                .mapToDouble(t -> t.getQuantity() * t.getProduct().getPrice())
                .sum();
    }

    public double getDiscountValue() {
        return cart.values().stream()
                .mapToDouble(t ->
                        (t.getProduct().getPromotionPrice() > 0 ?
                                t.getProduct().getPromotionPrice() : t.getProduct().getPrice()) * t.getQuantity())
                .sum();
    }
}
