package com.example.com.e_com.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private LocalDateTime createdAt;
    private BigDecimal totalAmount;
    private String status;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<OrderItem> items = new ArrayList<>();

    @PrePersist  // This method is called before the entity is persisted (saved) to the database. It ensures that certain fields are set to default values if they are not already set.
    public void prePersist() {
        if (this.createdAt == null) this.createdAt = LocalDateTime.now(java.time.Clock.systemUTC());
        if (this.totalAmount == null) {
            this.totalAmount = this.items.stream()
                    .map(i -> i.getPriceAtPurchase().multiply(java.math.BigDecimal.valueOf(i.getQuantity())))
                    .reduce(java.math.BigDecimal.ZERO, java.math.BigDecimal::add);
        }
        if (this.status == null) this.status = "CREATED";
    }


    










    

    


}
