package com.webapp.tdastore.controller.web;


import com.webapp.tdastore.data.entities.ItemShoppingCart;
import com.webapp.tdastore.data.entities.Product;
import com.webapp.tdastore.data.exception.CustomExceptionRuntime;
import com.webapp.tdastore.data.payload.CartItemRequest;
import com.webapp.tdastore.data.payload.response.CartItemResponse;
import com.webapp.tdastore.data.payload.response.UpdateCartResponse;
import com.webapp.tdastore.security.CustomUserDetails;
import com.webapp.tdastore.services.ItemShoppingCartService;
import com.webapp.tdastore.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/v1/cart")
public class CartRestController {
    @Autowired
    private ProductService productService;
    @Autowired
    private ItemShoppingCartService cartDbService;


    private Authentication auth;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<CartItemResponse> getShoppingCartPage() {
        auth = SecurityContextHolder.getContext().getAuthentication();
        long userId = ((CustomUserDetails) auth.getPrincipal()).getUser().getUserId();
        List<ItemShoppingCart> items = cartDbService.findAllByUserId(userId);
        if (items.size() < 1)
            throw new CustomExceptionRuntime(200, "Empty cart");
        List<CartItemResponse> response = items.stream().map(t -> {
            CartItemResponse item = new CartItemResponse();
            item.setProductInfo(t.getProduct());
            item.setQuantity(t.getQuantity());
            item.setItemId(t.getItemId());
            item.setAmount(t.getQuantity() * (t.getProduct().getPromotionPrice() > 0 ?
                    t.getProduct().getPromotionPrice() :
                    t.getProduct().getPrice()));
            return item;
        }).collect(Collectors.toList());
        return response;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity addToCart(@RequestBody CartItemRequest item) {
        Product product = productService.findProductByCode(item.getProductCode());
        auth = SecurityContextHolder.getContext().getAuthentication();
        //check product and quantity not null
        if (product != null && item.getQuantity() > 0) {
            //handle for anonymous or user login
            long userId = ((CustomUserDetails) auth.getPrincipal()).getUser().getUserId();
            cartDbService.insert(item.getProductCode(), item.getQuantity(), userId);
            return ResponseEntity.status(HttpStatus.OK).body("SUCCESS: Add " + item.getQuantity() +
                    " product[" + item.getProductCode() + "]");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("FAIL: Not found product");

    }

    @RequestMapping(value = "/{productCode}", method = RequestMethod.PUT)
    public ResponseEntity updateQuantity(@PathVariable String productCode,
                                         @RequestParam Integer quantity) {
        auth = SecurityContextHolder.getContext().getAuthentication();
        Product product = productService.findProductByCode(productCode);
        if (product != null && quantity >= 0) {
            long userId = ((CustomUserDetails) auth.getPrincipal()).getUser().getUserId();
            cartDbService.updateQuantity(userId, productCode, quantity);
            return ResponseEntity.status(HttpStatus.OK).body(new UpdateCartResponse(
                    "Update quantity success",
                    cartDbService.totalPriceForItem(userId),
                    cartDbService.totalAmountHasDiscount(userId),
                    cartDbService.size(userId)
            ));

        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("NOT FOUND - FAIL");
    }

    @RequestMapping(value = "/{productCode}", method = RequestMethod.DELETE)
    public ResponseEntity deleteItems(@PathVariable String productCode) {
        auth = SecurityContextHolder.getContext().getAuthentication();
        Product product = productService.findProductByCode(productCode);
        if (product != null) {
            long userId = ((CustomUserDetails) auth.getPrincipal()).getUser().getUserId();
            cartDbService.remove(userId, productCode);
            return ResponseEntity.status(HttpStatus.OK).body(new UpdateCartResponse(
                    "Update quantity success",
                    cartDbService.totalPriceForItem(userId),
                    cartDbService.totalAmountHasDiscount(userId),
                    cartDbService.size(userId)
            ));

        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("NOT FOUND - FAIL");
    }

    @RequestMapping(value = "/total-quantity", method = RequestMethod.GET)
    public ResponseEntity getQuantity() {
        int quantity = 0;
        auth = SecurityContextHolder.getContext().getAuthentication();

        long userId = ((CustomUserDetails) auth.getPrincipal()).getUser().getUserId();
        quantity = cartDbService.size(userId);

        return ResponseEntity.status(HttpStatus.OK).body(quantity);
    }

    @RequestMapping(value = "/total-amount", method = RequestMethod.GET)
    public ResponseEntity getTotalAmountFromCart() {
        double amount = getTotalValueFromCart(cartDbService::totalPriceForItem);
        return ResponseEntity.status(HttpStatus.OK).body(amount);
    }

    @RequestMapping(value = "/total-discount", method = RequestMethod.GET)
    public ResponseEntity getTotalDiscountFromCart() {
        double discount = getTotalValueFromCart(cartDbService::totalAmountHasDiscount);
        return ResponseEntity.status(HttpStatus.OK).body(discount);
    }

    //Function<Long, Double> mean parameter type long and return type is double
    //apply mean call object of cartValueFunction execute
    private double getTotalValueFromCart(Function<Long, Double> cartValueFunction) {
        auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth instanceof AnonymousAuthenticationToken) {
            throw new CustomExceptionRuntime(401, "Get item from shopping cart has failed. " +
                    "Cause missing jwt in header");
        } else {
            long userId = ((CustomUserDetails) auth.getPrincipal()).getUser().getUserId();
            return cartValueFunction.apply(userId);
        }
    }
}
