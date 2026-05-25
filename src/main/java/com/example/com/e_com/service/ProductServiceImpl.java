package com.example.com.e_com.service;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.example.com.e_com.dto.ProductRequest;
import com.example.com.e_com.dto.ProductResponse;
import com.example.com.e_com.model.Product;
import com.example.com.e_com.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepo;

    @Override
    public List<ProductResponse> getAllProducts() {
        return productRepo.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    //pagination 
    @Override
    public List<ProductResponse> getProductsPage(int page, int size) {
        return productRepo.findAll(PageRequest.of(page, size)).stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public ProductResponse getProductById(Long id) {
        Product product = productRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        return toResponse(product);
    }

    @Override
    public List<ProductResponse> searchProducts(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return getAllProducts();
            
        }
        String normalized = keyword.trim();
        return productRepo.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(normalized, normalized).stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public ProductResponse createProduct(ProductRequest req) {
        Product product = fromRequest(req);
        return toResponse(productRepo.save(product));
    }

    @Override
    public ProductResponse updateProduct(Long id, ProductRequest req) {
        Product existing = productRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        existing.setName(req.getName());
        existing.setDescription(req.getDescription());
        existing.setPrice(req.getPrice());
        return toResponse(productRepo.save(existing));
    }

    @Override
    public void deleteProduct(Long id) {
        productRepo.deleteById(id);
    }

    private ProductResponse toResponse(Product product) {
        return new ProductResponse(product.getId(), product.getName(), product.getDescription(), product.getPrice());
    }

    private Product fromRequest(ProductRequest req) {
        return new Product(null, req.getName(), req.getDescription(), req.getPrice());
    }
}
