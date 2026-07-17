package com.example.com.e_com.service;

import org.springframework.stereotype.Service;

import com.example.com.e_com.dto.CartRequest;
import com.example.com.e_com.model.Cart;
import com.example.com.e_com.model.CartItem;
import com.example.com.e_com.model.Product;
import com.example.com.e_com.repository.CartItemRepository;
import com.example.com.e_com.repository.CartRepository;
import com.example.com.e_com.repository.ProductRepository;
import com.example.com.e_com.exception.ResourceNotFoundException;
import com.example.com.e_com.exception.BadRequestException;

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
                .orElseGet(() -> cartRepository.save(new Cart()));  ///agar cart exist nahi karta to new cart create kar do add 

        Product product =
                productRepository.findById(
                        request.getProductId())
                .orElseThrow(
                        () -> new ResourceNotFoundException(
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
                                        new ResourceNotFoundException(
                                                        "Cart not found"));
        }



    @Override
        public Cart removeFromCart(
                Long cartId,
                CartRequest request) {
        
                Cart cart = cartRepository.findById(cartId)
                        .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));

                CartItem itemToRemove = cart.getItems().stream()
                        .filter(item -> item.getProduct().getId().equals(request.getProductId()))
                        .findFirst()
                        .orElseThrow(() -> new BadRequestException("Product not in cart"));
        
                if (itemToRemove.getQuantity() > request.getQuantity()) {
                itemToRemove.setQuantity(itemToRemove.getQuantity() - request.getQuantity());
                cartItemRepository.save(itemToRemove);
                } else {
                cart.getItems().remove(itemToRemove);
                cartItemRepository.delete(itemToRemove);
                }
        
                return cartRepository.save(cart);
        }
}

//explainantion of what we id here in removeFromCart method in simple -
//1. We first fetch the cart using the cartId. If the cart doesn't exist, we throw an exception.
//2. We then look for the CartItem in the cart that matches the productId from the CartRequest. If we don't find such an item, we throw another exception.
//3. If the item exists and its quantity is greater than the quantity we want to remove, we simply reduce the quantity of that item and save it.
//4. If the quantity to remove is greater than or equal to the existing quantity, we remove the item from the cart and delete it from the repository.
//5. Finally, we save the updated cart and return it.
