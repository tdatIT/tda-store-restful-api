package com.webapp.tdastore.services;

import com.webapp.tdastore.data.dto.OrderDTO;
import com.webapp.tdastore.data.entities.Order;
import com.webapp.tdastore.data.payload.response.OrderResponse;

import java.util.List;

public interface OrderService {
    List<Order> lastOrderRecentOfUser(long userId, int page, int size);

    boolean validationOrder(OrderDTO dto);

    Order getOrderByOrderId(long orderId);

    void insertOrderForUser(OrderDTO dto);

    void updateOrder(Order order);

    OrderResponse mapOrderEntityToResponse(Order order);

}
