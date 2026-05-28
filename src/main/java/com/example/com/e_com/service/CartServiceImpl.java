package com.example.com.e_com.service;

import org.springframework.stereotype.Service;

import com.example.com.e_com.dto.CartRequest;
import com.example.com.e_com.model.Cart;
import com.example.com.e_com.model.CartItem;
import com.example.com.e_com.model.Product;
import com.example.com.e_com.repository.CartItemRepository;
import com.example.com.e_com.repository.CartRepository;
import com.example.com.e_com.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;

    private final ProductRepository
            productRepository;

    private final CartItemRepository
            cartItemRepository;


    @Override
    public Cart addToCart(
            Long cartId,
            CartRequest request){

        Cart cart = cartRepository.findById(cartId)
                .orElseGet(() -> cartRepository.save(new Cart()));

        Product product =
                productRepository.findById(
                        request.getProductId())
                .orElseThrow(
                        () -> new RuntimeException(
                                "Product not found"));

        CartItem item = new CartItem();
        item.setCart(cart);
        item.setProduct(product);
        item.setQuantity(request.getQuantity());

        cartItemRepository.save(item);
        cart.getItems().add(item);

        return cartRepository.save(cart);
    }

     @Override       
    public Cart getCart(Long cartId) {
        return cartRepository.findById(cartId).orElseThrow(() ->
                    new RuntimeException(
                            "Cart not found"));
    }

}
