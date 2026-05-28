package com.example.com.e_com.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Entity
@Table(name = "cart_items") 
@Data // Lombok annotation to generate getters, setters, toString, equals, and hashCode
@NoArgsConstructor // Lombok annotation to generate a no-argument constructor
@AllArgsConstructor 
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name="cart_id") // This annotation specifies that the cart_id column in the cart_items table is a foreign key that references the primary key of the Cart entity. It establishes a many-to-one relationship between CartItem and Cart, meaning that multiple CartItem entities can be associated with a single Cart entity.
    @JsonBackReference
    private Cart cart;


    @ManyToOne
    @JoinColumn(name="product_id") // This annotation specifies that the product_id column in the cart_items table is a foreign key that references the primary key of the Product entity. It establishes a many-to-one relationship between CartItem and Product, meaning that multiple CartItem entities can be associated with a single Product entity.
    private Product product;


    private Integer quantity;


    
}


// Cart
// └── many CartItems

// CartItem
// └── one Product
