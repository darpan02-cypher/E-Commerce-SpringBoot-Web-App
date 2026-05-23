package com.example.com.e_com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.com.e_com.model.Product;

@Repository //DAO layer for Product entity
public interface ProductRepository extends JpaRepository<Product, Long> {
    //JPA provides basic CRUD methods, so no need to define anything here for now
}
