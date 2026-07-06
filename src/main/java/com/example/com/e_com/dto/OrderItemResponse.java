package com.example.com.e_com.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemResponse {
    private Long id;
    private ProductResponse product;
    private int quantity;
    private BigDecimal priceAtPurchase;
}
