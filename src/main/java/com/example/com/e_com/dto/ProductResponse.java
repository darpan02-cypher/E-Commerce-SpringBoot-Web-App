package com.example.com.e_com.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {

    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    
}


//ProductResponse is a DTO that represents the data that will be sent back to the client in response to product-related API requests. It typically contains fields that are relevant for the client to display or use, such as the product's id, name, description, and price. This class is used in the service layer to create a response object that can be returned to the controller, which then sends it back to the client as part of the API response. By using a separate DTO for responses, we can control exactly what data is exposed to the client and also decouple our internal data model from the external API contract.

