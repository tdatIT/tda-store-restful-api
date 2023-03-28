package com.webapp.tdastore.services;

import com.webapp.tdastore.data.dto.OrderDTO;
import com.webapp.tdastore.data.entities.Order;
import com.webapp.tdastore.data.entities.User;
import com.webapp.tdastore.data.entities.UserAddress;

public interface OrderService {
    void insertOrderForGuest(OrderDTO dto);

    void insertOrderForUser(User us, UserAddress address);

    void updateOrder(Order order);
}
