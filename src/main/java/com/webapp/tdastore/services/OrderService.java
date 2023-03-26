package com.webapp.tdastore.services;

import com.webapp.tdastore.dto.OrderDTO;
import com.webapp.tdastore.entities.Order;
import com.webapp.tdastore.entities.User;
import com.webapp.tdastore.entities.UserAddress;

public interface OrderService {
    void insertOrderForGuest(OrderDTO dto);

    void insertOrderForUser(User us, UserAddress address);

    void updateOrder(Order order);
}
