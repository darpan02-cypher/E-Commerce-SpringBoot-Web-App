package com.example.com.e_com.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.com.e_com.dto.ProductRequest;
import com.example.com.e_com.dto.ProductResponse;
import com.example.com.e_com.exception.ResourceNotFoundException;
import com.example.com.e_com.model.Product;
import com.example.com.e_com.repository.ProductRepository;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepo;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    @DisplayName("getProductById returns response when product exists")
    void getProductById_found() {
        Product product = new Product(1L, "Widget", "Nice widget", new BigDecimal("9.99"));
        when(productRepo.findById(1L)).thenReturn(Optional.of(product));

        ProductResponse res = productService.getProductById(1L);

        assertEquals(1L, res.getId());
        assertEquals("Widget", res.getName());
        assertEquals(new BigDecimal("9.99"), res.getPrice());
        verify(productRepo).findById(1L); // Verify that the repository method was called with the correct argument - i.e the product ID 1L
    }

    @Test
    @DisplayName("getProductById throws when not found")
    void getProductById_notFound() {
        when(productRepo.findById(99L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> productService.getProductById(99L));
    }

    @Test
    @DisplayName("createProduct saves and returns created product")
    void createProduct_saves() {
        ProductRequest req = new ProductRequest("Gizmo", "Cool gizmo", new BigDecimal("19.95"));
        Product saved = new Product(2L, req.getName(), req.getDescription(), req.getPrice());
        when(productRepo.save(org.mockito.ArgumentMatchers.any(Product.class))).thenReturn(saved);

        ProductResponse resp = productService.createProduct(req);

        assertEquals(2L, resp.getId());
        assertEquals("Gizmo", resp.getName());

        ArgumentCaptor<Product> captor = ArgumentCaptor.forClass(Product.class);
        verify(productRepo).save(captor.capture());
        Product toSave = captor.getValue();
        assertEquals(req.getName(), toSave.getName());
    }

    @Test
    @DisplayName("searchProducts with empty keyword returns all products")
    void searchProducts_emptyKeyword_returnsAll() {
        Product p1 = new Product(1L, "A", "a", new BigDecimal("1"));
        Product p2 = new Product(2L, "B", "b", new BigDecimal("2"));
        when(productRepo.findAll()).thenReturn(List.of(p1, p2));

        List<ProductResponse> out = productService.searchProducts("   ");

        assertEquals(2, out.size());
        verify(productRepo).findAll();
    }
}
