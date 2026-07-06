package com.example.com.e_com.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.com.e_com.dto.OrderItemResponse;
import com.example.com.e_com.dto.OrderResponse;
import com.example.com.e_com.dto.ProductResponse;
import com.example.com.e_com.model.Cart;
import com.example.com.e_com.model.CartItem;
import com.example.com.e_com.model.Order;
import com.example.com.e_com.model.OrderItem;
import com.example.com.e_com.repository.CartItemRepository;
import com.example.com.e_com.repository.CartRepository;

import com.example.com.e_com.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    @Override
    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public OrderResponse getOrderById(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
        return toResponse(order);
    }

    @Override
    public OrderResponse createOrderFromCart(Long cartId) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new RuntimeException("Cart not found"));
        if (cart.getItems().isEmpty()) throw new RuntimeException("Cart is empty");

        Order order = new Order();
        // build order items
        for (CartItem ci : cart.getItems()) {
            OrderItem oi = new OrderItem();
            oi.setProduct(ci.getProduct());
            oi.setQuantity(ci.getQuantity());
            oi.setPriceAtPurchase(ci.getProduct().getPrice());
            oi.setOrder(order);
            order.getItems().add(oi);
        }

        Order saved = orderRepository.save(order);

        // clear cart
        cartItemRepository.deleteAll(cart.getItems());
        cart.getItems().clear();
        cartRepository.save(cart);

        return toResponse(saved);
    }

    @Override
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    private OrderResponse toResponse(Order order) {
        List<OrderItemResponse> items = order.getItems().stream().map(i -> {
            ProductResponse p = new ProductResponse(i.getProduct().getId(), i.getProduct().getName(), i.getProduct().getDescription(), i.getProduct().getPrice());
            return new OrderItemResponse(i.getId(), p, i.getQuantity(), i.getPriceAtPurchase());
        }).toList();

        return new OrderResponse(order.getId(), order.getCreatedAt(), order.getTotalAmount(), order.getStatus(), items);
    }
}
