package com.example.com.e_com.service;

import java.util.List;

import com.example.com.e_com.dto.OrderResponse;

public interface OrderService {
    List<OrderResponse> getAllOrders();
    OrderResponse getOrderById(Long id);
    OrderResponse createOrderFromCart(Long cartId);
    void deleteOrder(Long id);
}
