package com.example.com.e_com.service;

import java.util.List;

import org.hibernate.query.Page;

import com.example.com.e_com.dto.ProductRequest;
import com.example.com.e_com.dto.ProductResponse;

public interface  ProductService {
    //Define service methods for product operations (e.g., getAllProducts, getProductById, createProduct, etc.)
    List<ProductResponse> getAllProducts();
    ProductResponse getProductById(Long id);
    List<ProductResponse> searchProducts(String keyword);
    ProductResponse createProduct(ProductRequest req);
    ProductResponse updateProduct(Long id, ProductRequest req);
    void deleteProduct(Long id);
            
//very imp to note her - we are using ProductResponse instead of Product entity in service layer to decouple internal model from external API contract. This allows us to change our database schema without affecting API clients, and also to control exactly what data is exposed in API responses.
//tht makes the diffrence in using DTOs in service layer instead of entities. 

    //pagination 
    List<ProductResponse> getProductsPage(int page, int size);
    
}
