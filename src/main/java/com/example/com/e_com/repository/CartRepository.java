package com.example.com.e_com.repository;

import com.example.com.e_com.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository 
public interface CartRepository extends JpaRepository<Cart,Long> {

    


    
}
