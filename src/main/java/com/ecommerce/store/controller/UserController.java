package com.ecommerce.store.controller;

import com.ecommerce.store.dto.OrderItemDto;
import com.ecommerce.store.entity.Order;
import com.ecommerce.store.entity.User;
import com.ecommerce.store.service.JWTService;
import com.ecommerce.store.service.OrderService;
import com.ecommerce.store.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    private JWTService jwtService;

    @Autowired
    private UserService userService;
    @Autowired
    private OrderService orderService;

    @PostMapping("/api/v1/user/createorder")
    public Order createOrder(@RequestBody List<OrderItemDto> orderItemDtos,
                             @RequestHeader("Authorization") String token) {
        token = token.substring(7);
        String userEmail = jwtService.extractUsername(token);
        if (userService.userExists(userEmail)) {
            User user = (User) userService.loadUserByUsername(userEmail);
            return orderService.createOrder(orderItemDtos, user);
        }
        return null;
    }

    @GetMapping("/api/v1/user/getorders")
    public List<Order> getOrders(@RequestHeader("Authorization") String token) {
        token = token.substring(7);
        String userEmail = jwtService.extractUsername(token);
        if (userService.userExists(userEmail)) {
            User user = (User) userService.loadUserByUsername(userEmail);
            return orderService.getUserOrders(user);
        }
        return null;
    }

}
