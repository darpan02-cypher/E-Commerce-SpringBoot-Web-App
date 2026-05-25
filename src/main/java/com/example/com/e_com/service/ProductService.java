package com.example.com.e_com.service;

import java.util.List;

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
            


    
}
