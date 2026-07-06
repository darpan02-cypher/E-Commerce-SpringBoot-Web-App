package com.example.com.e_com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.com.e_com.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

}
