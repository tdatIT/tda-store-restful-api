package com.webapp.tdastore.services.impl;

import com.webapp.tdastore.dto.OrderDTO;
import com.webapp.tdastore.entities.*;
import com.webapp.tdastore.repositories.OrderRepos;
import com.webapp.tdastore.repositories.ProductRepos;
import com.webapp.tdastore.repositories.UserRepos;
import com.webapp.tdastore.services.ItemShoppingCartService;
import com.webapp.tdastore.services.OrderService;
import com.webapp.tdastore.services.ShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepos orderRepos;
    @Autowired
    private ProductRepos productRepos;
    @Autowired
    private UserRepos userRepos;
    @Autowired
    private ShoppingCart sessionCart;
    @Autowired
    private ItemShoppingCartService dbCart;
    @Autowired
    private ShippingService shippingService;

    @Override
    public void insertOrderForGuest(OrderDTO dto) {

        //find user has exist in db
        User user = userRepos.findUserByEmail(dto.getEmail());
        //if user had exist then add new address and insert order
        //else create new user and address
        //get item in session cart add to order
        if (user == null) {
            user = new User();
            user.setFirstname(dto.getFirstname());
            user.setLastname(dto.getLastname());
            user.setPhone(dto.getPhone());
            user.setEmail(dto.getEmail());
            user.setAddress(new ArrayList<>());
            user.getAddress().add(dto.getDefaultAddress());
            user.setStatus(true);
        } else {
            user.getAddress().add(dto.getDefaultAddress());
        }
        //save user
        userRepos.save(user);
        //create order instance
        Order order = new Order();
        order.setAddress(dto.getDefaultAddress());
        order.setOrderStatus(Order.CHECK_STATUS);
        //set price
        order.setTotal(sessionCart.getTotalPrice());
        order.setDiscount(sessionCart.getDiscountValue());
        //set time
        order.setCreateDate(new Timestamp(new Date().getTime()));
        order.setShippingCosts(
                shippingService.getPriceFromUserAddress(
                        dto.getDefaultAddress().getProvince(),
                        dto.getDefaultAddress().getDistrict())
        );
        order.setUser(user);
        //create order items list from session cart
        order.setItems(sessionCart.getCartItems().stream()
                .map(item -> {
                    OrderItems i = new OrderItems();
                    Product p = productRepos.findProductByProductCode(item.getProduct().getProductCode());
                    i.setProduct(p);
                    i.setPrice(item.getAmount());
                    i.setQuantity(item.getQuantity());
                    i.setOrder(order);
                    return i;
                }).collect(Collectors.toList()));
        //insert new order into db
        orderRepos.save(order);
        sessionCart.getCart().clear();
    }

    @Override
    public void insertOrderForUser(User us, UserAddress address) {
        List<ItemShoppingCart> cartData = dbCart.findAllByUserId(us.getUserId());
        Order order = new Order();
        order.setAddress(address);
        order.setOrderStatus(Order.CHECK_STATUS);
        //set price
        order.setTotal(dbCart.totalPriceForItem(us.getUserId()));
        order.setDiscount(dbCart.totalAmountHasDiscount(us.getUserId()));
        //set time
        order.setCreateDate(new Timestamp(new Date().getTime()));
        order.setShippingCosts(
                shippingService.getPriceFromUserAddress(
                        address.getProvince(),
                        address.getDistrict())
        );
        order.setUser(us);
        //create order items list from user cart database
        order.setItems(cartData.stream()
                .map(item -> {
                    OrderItems i = new OrderItems();
                    Product p = productRepos.findProductByProductCode(item.getProduct().getProductCode());
                    i.setProduct(p);
                    i.setQuantity(item.getQuantity());
                    i.setPrice(item.getAmount());
                    i.setOrder(order);
                    return i;
                }).collect(Collectors.toList()));
        //insert new order into db
        orderRepos.save(order);
        dbCart.clear(us.getUserId());
    }

    @Override
    public void updateOrder(Order order) {

    }
}
