package com.example.com.e_com.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.com.e_com.dto.CartRequest;
import com.example.com.e_com.model.Cart;
import com.example.com.e_com.service.CartService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;


    @PostMapping("/{cartId}/add")
    public ResponseEntity<Cart>
    addToCart(

            @PathVariable Long cartId,

            @RequestBody CartRequest request){

        return ResponseEntity.ok(
                cartService.addToCart(
                        cartId,
                        request));
    }


    @GetMapping("/{cartId}")
    public ResponseEntity<Cart>
    getCart(@PathVariable Long cartId){

        return ResponseEntity.ok(
                cartService.getCart(cartId));
    }


}