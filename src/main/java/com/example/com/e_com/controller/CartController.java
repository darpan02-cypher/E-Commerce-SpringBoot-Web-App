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

    //removeFromCart endpoint
    @PostMapping("/{cartId}/remove")
    public ResponseEntity<Cart>
    removeFromCart(
            @PathVariable Long cartId,
            @RequestBody CartRequest request){

        return ResponseEntity.ok(
                cartService.removeFromCart(
                        cartId,
                        request));
    }

}

//explanation of what we id here in removeFromCart method in controller in simple -
//1. We define a POST endpoint at /api/cart/{cartId}/remove which takes the cartId as a path variable and the CartRequest as a request body.
//2. We call the removeFromCart method of the CartService, passing the cartId and the CartRequest. This method will handle the logic of removing the specified quantity of the product from the cart.
//3. Finally, we return the updated cart in the response.