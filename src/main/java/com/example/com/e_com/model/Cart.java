package com.example.com.e_com.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.CascadeType;

@Entity
@Data // Lombok annotation to generate getters, setters, toString, equals, and hashCode
@Table(name = "carts")
@NoArgsConstructor // Lombok annotation to generate a no-argument constructor
@AllArgsConstructor 
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL) // mappedBy - is used to specify the field in the CartItem entity that owns the relationship. In this case, it indicates that the cart field in CartItem is the owner of the relationship. CascadeType.ALL - means that any operation (like persist, merge, remove) performed on the Cart entity will also be cascaded to the associated CartItem entities. This ensures that when a Cart is saved or deleted, all related CartItems are also saved or deleted accordingly.
    @JsonManagedReference
    private List<CartItem> items = new ArrayList<>();   // Initialize the items list to avoid NullPointerException when adding items to the cart





    
}
