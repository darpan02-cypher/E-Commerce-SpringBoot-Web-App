package com.example.com.e_com.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.com.e_com.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
