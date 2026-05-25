package com.example.com.e_com.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.com.e_com.model.Product;

@Repository //DAO layer for Product entity
public interface ProductRepository extends JpaRepository<Product, Long> {

    //search is a custom query method
    List<Product> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String name, String description);
//we have added this method to support search functionality in our application. It allows us to find products whose name or description contains a given keyword, ignoring case sensitivity. This is useful for implementing a search feature in our product catalog, enabling users to easily find products based on partial matches in their names or descriptions.
//but not included other CRUD methods like findAll, findById, save, deleteById etc. because those are already provided by JpaRepository interface which we are extending. So we only need to define custom query methods that are specific to our application's needs, while the basic CRUD operations are inherited from JpaRepository.

     //future scope 
     //findByPriceLessThan -- custom quesry 



}
