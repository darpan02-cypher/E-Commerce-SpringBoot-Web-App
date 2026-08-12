package com.example.com.e_com.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.com.e_com.dto.OrderResponse;
import com.example.com.e_com.service.OrderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping({"/orders", "/api/orders"})
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAll() {
        MDC.put("event", "ORDER_LIST");
        logger.info("API GET /orders - fetch all orders");
        MDC.remove("event");
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getById(@PathVariable Long id) {
        MDC.put("event", "ORDER_FETCH");
        logger.info("API GET /orders/{}", id);
        MDC.remove("event");
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @PostMapping("/{cartId}")
    public ResponseEntity<OrderResponse> createFromCart(@PathVariable Long cartId) {
        MDC.put("event", "ORDER_CREATED");
        logger.info("API POST /orders/{} - create order from cart", cartId);
        MDC.remove("event");
        return new ResponseEntity<>(orderService.createOrderFromCart(cartId), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        MDC.put("event", "ORDER_CANCELLED");
        logger.info("API DELETE /orders/{}", id);
        MDC.remove("event");
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}
