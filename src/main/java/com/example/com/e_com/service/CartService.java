package com.example.com.e_com.service;

import com.example.com.e_com.dto.CartRequest;
import com.example.com.e_com.model.Cart;

public interface CartService {

    Cart addToCart(
            Long cartId,
            CartRequest request);


    Cart getCart(Long cartId);
}
