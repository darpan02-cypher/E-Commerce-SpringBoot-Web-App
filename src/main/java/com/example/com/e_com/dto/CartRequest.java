package com.example.com.e_com.dto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class CartRequest {

    private Long productId;

    private Integer quantity;
    
}
