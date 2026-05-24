package com.example.com.e_com.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// DTO for product creation request-- this is waht the client will send 
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank
    private String description;
    
    @Positive(message = "Price must be positive")
    private BigDecimal price;
    
}


//DTO class is used to transfer data between different layers of the application, such as from the controller to the service layer. It helps to encapsulate the data and can also include validation annotations to ensure that the incoming data meets certain criteria before it is processed further. In this case, ProductRequest is a DTO that represents the data needed to create a new product, including its name, description, and price.
//it contains information about the product that the client wants to create, and it can be used in the controller to receive the data from the client and then pass it to the service layer for further processing, such as saving it to the database.