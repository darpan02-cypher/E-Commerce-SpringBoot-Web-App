package com.example.com.e_com.service ;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

//Service layer- contains business logic and interacts with the repository layer to perform operations on the data. It acts as an intermediary between the controller layer (which handles HTTP requests) and the repository layer (which interacts with the database). The service layer is responsible for implementing the core functionality of the application, such as processing data, applying business rules, and managing transactions. By separating concerns in this way, we can keep our code organized, maintainable, and scalable.
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepo;

    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }

    public Product getProductById(Long id) {
        return productRepo.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public Product createProduct(Product product) {
        return productRepo.save(product);
    }

    public Product updateProduct(Long id, Product updatedProduct) {
        Product existing = getProductById(id);
        existing.setName(updatedProduct.getName());
        existing.setPrice(updatedProduct.getPrice());
        existing.setDescription(updatedProduct.getDescription());
        return productRepo.save(existing);
    }

    public void deleteProduct(Long id) {
        productRepo.deleteById(id);
    }
    //productRepo is what - we use to interact with the database for Product entity (DAO layer) and we are using it from the service layer to perform business logic and data manipulation before sending it to the controller layer.
}
