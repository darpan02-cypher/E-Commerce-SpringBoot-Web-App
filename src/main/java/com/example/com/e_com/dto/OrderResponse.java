package com.example.com.e_com.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {
    private Long id;
    private LocalDateTime createdAt;
    private BigDecimal totalAmount;
    private String status;
    private List<OrderItemResponse> items;
}
