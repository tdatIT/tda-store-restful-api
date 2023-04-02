package com.webapp.tdastore.services.impl;

import com.webapp.tdastore.data.dto.OrderDTO;
import com.webapp.tdastore.data.entities.*;
import com.webapp.tdastore.data.payload.response.OrderItemResp;
import com.webapp.tdastore.data.payload.response.OrderResponse;
import com.webapp.tdastore.data.repositories.*;
import com.webapp.tdastore.services.ItemShoppingCartService;
import com.webapp.tdastore.services.OrderService;
import com.webapp.tdastore.services.ShippingService;
import lombok.extern.log4j.Log4j2;
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
@Log4j2
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepos orderRepos;
    @Autowired
    private ProductRepos productRepos;
    @Autowired
    private UserRepos userRepos;
    @Autowired
    private VoucherRepos voucherRepos;
    @Autowired
    private UserAddressRepo addressRepo;
    @Autowired
    private ItemShoppingCartService dbCart;
    @Autowired
    private ShippingService shippingService;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Order getOrderByOrderId(long orderId) {
        return orderRepos.findById(orderId).orElseThrow();
    }

    @Override
    public OrderResponse mapOrderEntityToResponse(Order order) {
        OrderResponse item = modelMapper.map(order, OrderResponse.class);
        item.setAddress_id(order.getAddress().getAddressId());
        item.setAddress_detail(order.getAddress().getDetail());
        item.setUserId(order.getUser().getUserId());
        item.setOrder_items(order.getItems().stream().map(o -> {
            OrderItemResp i = new OrderItemResp();
            i.setQuantity(o.getQuantity());
            i.setPrice(o.getPrice());
            i.setProductId(o.getProduct().getProductId());
            i.setProductCode(o.getProduct().getProductCode());
            i.setProductImg(o.getProduct().getImages().get(0).getUrlImage());
            i.setProductName(o.getProduct().getProductName());
            return i;
        }).collect(Collectors.toList()));
        return item;
    }

    @Override
    public List<Order> lastOrderRecentOfUser(long userId, int page, int size) {
        return orderRepos.findLastOrderByUserId(userId, PageRequest.of(page, size));
    }

    @Override
    @Transactional
    public long insertOrderForUser(OrderDTO dto) {
        //find item in cart from user request
        try {
            List<ItemShoppingCart> items = dbCart.findAllByItemsId(dto.getCart_items());
            //find user has exist in db
            Order order = modelMapper.map(dto, Order.class);
            order.setUser(new User(dto.getUserId()));
            order.setAddress(new UserAddress(dto.getAddress_id()));
            order.setCreateDate(new Timestamp(new Date().getTime()));
            order.setOrderStatus(Order.CHECK_STATUS);
            //set shipping cost
            UserAddress address = addressRepo.findById(dto.getAddress_id()).orElseThrow();
            order.setShippingCosts(
                    shippingService.getPriceFromUserAddress(
                            address.getProvince(),
                            address.getDistrict())
            );
            //set price and item from cart item
            order.setItems(items.stream()
                    .map(t -> {
                        OrderItems i = new OrderItems();
                        i.setProduct(t.getProduct());
                        i.setPrice(t.getAmount());
                        i.setQuantity(t.getQuantity());
                        i.setOrder(order);
                        return i;
                    }).collect(Collectors.toList()));
            order.setDiscount(dbCart.totalHaveDiscountSelectItem(items));
            order.setTotal(dbCart.totalAmountSelectItem(items));
            if (dto.getVoucher_code() != null) {
                Voucher v = voucherRepos.validateVoucherCode(dto.getVoucher_code());
                if (v != null) {
                    order.setVoucher(v);
                    order.setDiscount(order.getDiscount() - (order.getDiscount() * v.getDiscount()));
                }
            }
            //insert new order into db
            Order success = orderRepos.save(order);
            //remove item was created orders from user shopping cart
            items.forEach(t -> dbCart.removeItemWhenOrderItemWasCreated(t.getItemId()));
            //return orderId
            return success.getOrderId();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return 0;
    }

    @Transactional
    @Override
    public void updateOrder(Order order) {
        orderRepos.save(order);
    }
}
