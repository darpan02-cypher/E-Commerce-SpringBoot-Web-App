package com.example.com.e_com.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.example.com.e_com.dto.ProductRequest;
import com.example.com.e_com.dto.ProductResponse;
import com.example.com.e_com.exception.ResourceNotFoundException;
import com.example.com.e_com.model.Product;
import com.example.com.e_com.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor 
public class ProductServiceImpl implements ProductService {
    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
    private final ProductRepository productRepo; //productRepo is an instance of ProductRepository, which is a Spring Data JPA repository for the Product entity. It provides methods to perform CRUD operations on the Product table in the database.

    @Override
    public List<ProductResponse> getAllProducts() {
        logger.info("Fetching all products");
        return productRepo.findAll().stream()
            .map(this::toResponse)
            .toList();
    }

    //pagination 
    @Override
    public List<ProductResponse> getProductsPage(int page, int size) {
        logger.info("Fetching products page: page={}, size={}", page, size);
        return productRepo.findAll(PageRequest.of(page, size)).stream()
            .map(this::toResponse)
            .toList();
    }

    @Override
    public ProductResponse getProductById(Long id) {
        logger.info("Fetching product by id={}", id);
        Product product = productRepo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        return toResponse(product);
    }

    @Override
    public List<ProductResponse> searchProducts(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            logger.info("Search called with empty keyword, returning all products");
            return getAllProducts();
            
        }
        String normalized = keyword.trim();
        logger.info("Searching products with keyword={}", normalized);
        return productRepo.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(normalized, normalized).stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public ProductResponse createProduct(ProductRequest req) {
        logger.info("Creating product name={}", req.getName());
        Product product = fromRequest(req);
        return toResponse(productRepo.save(product));
    }

    @Override
    public ProductResponse updateProduct(Long id, ProductRequest req) {
        logger.info("Updating product id={}", id);
        Product existing = productRepo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        existing.setName(req.getName());
        existing.setDescription(req.getDescription());
        existing.setPrice(req.getPrice());
        return toResponse(productRepo.save(existing));
    }

    @Override
    public void deleteProduct(Long id) {
        logger.info("Deleting product id={}", id);
        productRepo.deleteById(id);
    }

    private ProductResponse toResponse(Product product) {
        return new ProductResponse(product.getId(), product.getName(), product.getDescription(), product.getPrice());
    }

    private Product fromRequest(ProductRequest req) {
        return new Product(null, req.getName(), req.getDescription(), req.getPrice());
    }
}
