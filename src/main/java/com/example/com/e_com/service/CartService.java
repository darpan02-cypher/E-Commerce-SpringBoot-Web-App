package com.example.com.e_com.service;

import com.example.com.e_com.dto.CartRequest;
import com.example.com.e_com.model.Cart;

public interface CartService {

    Cart addToCart(
            Long cartId,
            CartRequest request);


    Cart getCart(Long cartId);


    //Remove item from cart
    Cart removeFromCart(
            Long cartId,
            CartRequest request);   //cartRequest me productId aur quantity hoga, jise use karke cart item ko remove karna hai
}
