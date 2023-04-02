package com.webapp.tdastore.controller.web;

import com.webapp.tdastore.data.dto.OrderDTO;
import com.webapp.tdastore.data.entities.Order;
import com.webapp.tdastore.data.entities.User;
import com.webapp.tdastore.data.exception.CustomExceptionRuntime;
import com.webapp.tdastore.data.payload.response.OrderResponse;
import com.webapp.tdastore.security.CustomUserDetails;
import com.webapp.tdastore.services.OrderService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/orders")
@SecurityRequirement(name = "Bearer Authentication")
public class OrderRestController {
    @Autowired
    private OrderService orderService;

    private User getUserFromAuthentication() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return ((CustomUserDetails) auth.getPrincipal()).getUser();
    }

    @RequestMapping(value = "/user/last-order", method = RequestMethod.GET)
    public List<OrderResponse> getOrdersRecent(@RequestParam(required = false) Integer page,
                                               @RequestParam(required = false) Integer size) {
        if (page == null)
            page = 0;
        if (size == 0)
            size = 5;
        List<Order> orders = orderService
                .lastOrderRecentOfUser(getUserFromAuthentication().getUserId(), page, size);
        if (orders.size() < 1)
            throw new CustomExceptionRuntime(200, "The orders are empty");
        //Map from entity to object response
        return orders.stream()
                .map(t -> orderService.mapOrderEntityToResponse(t))
                .collect(Collectors.toList());
    }

    @RequestMapping(value = "/{orderId}", method = RequestMethod.GET)
    public OrderResponse getOrderFromUserById(@PathVariable long orderId) {
        Order order = orderService.getOrderByOrderId(orderId);
        if (order == null)
            throw new CustomExceptionRuntime(400, "The order not found with id");
        return orderService.mapOrderEntityToResponse(order);
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public ResponseEntity createOrderFromUserRequest(@Valid @RequestBody OrderDTO dto,
                                                     BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            throw new CustomExceptionRuntime(400, "The request failed. Please check the input data again.");
        //set user into order
        dto.setUserId(getUserFromAuthentication().getUserId());
        //create new order
        long orderId = orderService.insertOrderForUser(dto);
        if (orderId == 0)
            throw new CustomExceptionRuntime(400, "Failed to create the order. " +
                    "Please verify the input data and try again");
        return ResponseEntity.ok("The order was created successful with ID: " + orderId);
    }
}
