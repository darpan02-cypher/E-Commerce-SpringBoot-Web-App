package com.example.com.e_com.repository;

import com.example.com.e_com.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface CartItemRepository extends JpaRepository<CartItem,Long>{


    
}
