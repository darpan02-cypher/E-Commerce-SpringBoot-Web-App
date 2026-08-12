package com.example.com.e_com.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.com.e_com.dto.ProductRequest;
import com.example.com.e_com.dto.ProductResponse;
import com.example.com.e_com.service.ProductService;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping({"/products", "/api/products"})
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        MDC.put("event", "PRODUCT_LIST");
        logger.info("API GET /products - fetch all");
        MDC.remove("event");
        return ResponseEntity.ok(productService.getAllProducts());
    }
    //pagination with page and size params -- keeping default values for convenience - 3  
    @GetMapping("/page")
    public ResponseEntity<List<ProductResponse>> getProductsPage(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "3") int size) {
        MDC.put("event", "PRODUCT_LIST_PAGE");
        logger.info("API GET /products/page - page={}, size={}", page, size);
        MDC.remove("event");
        return ResponseEntity.ok(productService.getProductsPage(page, size));
    }   


    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id) {
        MDC.put("event", "PRODUCT_FETCH");
        logger.info("API GET /products/{}", id);
        MDC.remove("event");
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductResponse>> searchProducts(@RequestParam(value = "keyword", required = false) String keyword) {
        logger.info("API GET /products/search - keyword={}", keyword);
        return ResponseEntity.ok(productService.searchProducts(keyword));
    }

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductRequest req) {
        MDC.put("event", "PRODUCT_CREATED");
        logger.info("API POST /products - create name={}", req.getName());
        MDC.remove("event");
        return new ResponseEntity<>(productService.createProduct(req), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductRequest req) {
        MDC.put("event", "PRODUCT_UPDATED");
        logger.info("API PUT /products/{} - update", id);
        MDC.remove("event");
        return ResponseEntity.ok(productService.updateProduct(id, req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        logger.info("API DELETE /products/{}", id);
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    
    
}
