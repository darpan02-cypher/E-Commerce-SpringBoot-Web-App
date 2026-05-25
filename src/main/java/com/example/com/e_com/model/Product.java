package com.example.com.e_com.model;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data // Lombok annotation to generate getters, setters, toString, equals, and hashCode
@Table(name = "products")
@NoArgsConstructor // Lombok annotation to generate a no-argument constructor
@AllArgsConstructor // Lombok annotation to generate an all-arguments constructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;
    
    private BigDecimal price;  // Using BigDecimal for monetary values is a good practice to avoid floating-point issues

    // Getters and Setters not needed due to Lombok annotations

   
}

//Product feature end to end flow-step by step
//1. Create a Product entity class that represents the product data in the database. (will this cretate a table in the database? yes, it will create a table named "products" with columns corresponding to the fields in the Product class)
//2. Create a ProductRepository interface that extends JpaRepository to provide CRUD operations for the Product entity.
//3. Create a ProductService class that contains the business logic for managing products, such as  creating, retrieving, updating, and deleting products. This service will use the ProductRepository to interact with the database.
//4. Create a ProductController class that handles HTTP requests related to products. This controller will use the ProductService to perform operations on products and return appropriate responses to the client.
//5. Create DTO classes (ProductRequest and ProductResponse) to transfer data between the client and the server. ProductRequest will be used to receive data from the client when creating or updating a product, while ProductResponse will be used to send product data back to the client in responses.
//6. Implement validation in the ProductRequest DTO to ensure that the incoming data is valid before processing it in the service layer.


//in simple to remember entire Product flow 
//1. Product entity class represents the product data in the database and creates a table named "products".
//2. ProductRepository interface provides CRUD operations for the Product entity.
//3. ProductService class contains business logic for managing products and interacts with the ProductRepository to perform database operations.
//4. ProductController class handles HTTP requests related to products and uses the ProductService to perform operations and return responses to the client.
//5. DTO classes (ProductRequest and ProductResponse) are used to transfer data between the client and the server, with validation implemented in ProductRequest to ensure incoming data is valid before processing it in the service layer.    


//Interview question- //
//What is the purpose of the Product entity class in a Spring Boot application? - The Product entity class represents the product data in the database and is used to create a table named "products" with columns corresponding to the fields in the Product class. It serves as a blueprint for how product data is stored and managed in the application, allowing for easy interaction with the database through repositories and services.
//How does the Product entity class interact with the database in a Spring Boot application? - The Product entity class interacts with the database through the use of repositories, such as the ProductRepository interface that extends JpaRepository. This repository provides CRUD operations for the Product entity, allowing for easy management of product data in the database. The service layer, such as the ProductService class, uses the repository to perform business logic and data manipulation before sending it to the controller layer for handling HTTP requests and responses.
//What are the advantages of using Lombok annotations in the Product entity class? - Lombok annotations, such as @Data, @NoArgsConstructor, and @AllArgsConstructor, provide several advantages in the Product entity class. They automatically generate boilerplate code such as getters, setters, toString, equals, hashCode, and constructors, which reduces the amount of code that developers need to write and maintain. This leads to cleaner and more concise code, improves readability, and allows developers to focus on the core business logic rather than writing repetitive code. Additionally, it can help prevent errors that may arise from manually writing these methods and constructors.
//IWhy is BigDecimal used for the price field in the Product entity class? - BigDecimal is used for the price field in the Product entity class because it provides a more accurate representation of monetary values compared to floating-point types like double or float. BigDecimal allows for precise control over decimal places and avoids issues with rounding errors that can occur with floating-point arithmetic. This is especially important when dealing with financial data, where accuracy is crucial. Using BigDecimal ensures that calculations involving prices are reliable and consistent, which is essential for an e-commerce application where pricing accuracy can impact customer trust and business operations.
//How does the Product entity class contribute to the overall architecture of a Spring Boot application? - The Product entity class contributes to the overall architecture of a Spring Boot application by serving as a fundamental building block for the data layer. It defines the structure of the product data and how it is stored in the database, allowing for seamless integration with repositories and services. The entity class enables the application to perform CRUD operations on product data, which is essential for managing products in an e-commerce application. By using JPA annotations, it also facilitates object-relational mapping, making it easier to interact with the database and maintain a clean separation of concerns between different layers of the application.
// What is the significance of the @Entity annotation in the Product class? - The @Entity annotation in the Product class indicates that this class is a JPA entity, which means it is a persistent Java object that will be mapped to a database table. This annotation tells the JPA provider (such as Hibernate) to manage instances of this class and to create a corresponding table in the database with columns that match the fields of the class. It is essential for enabling ORM (Object-Relational Mapping) functionality, allowing developers to work with Java objects while the framework handles the underlying database interactions.
// What is the purpose of the @Id and @GeneratedValue annotations in the Product class? - The @Id annotation in the Product class indicates that the field it annotates (in this case, 'id') is the primary key of the entity. This means that it uniquely identifies each instance of the Product in the database. The @GeneratedValue annotation specifies that the value of this primary key should be automatically generated by the database, using a specified strategy (such as GenerationType.IDENTITY). This allows for automatic handling of primary key values when new products are created, ensuring that each product has a unique identifier without requiring manual assignment.


//////////Important Faang level question- How would you design the Product entity class to support additional features such as product categories, inventory management, or product reviews? - To design the Product entity class to support additional features such as product categories, inventory management, or product reviews, I would consider the following approaches: 
//////////1. Product Categories: I would create a separate Category entity class that represents product categories. The Product class would have a many-to-one relationship with the Category class, allowing each product to belong to a specific category. This would involve adding a field in the Product class for the category and using JPA annotations to define the relationship.
//////////2. Inventory Management: I would add fields to the Product class to track inventory levels, such as 'stockQuantity' to represent the number of items available in stock. I would also consider creating an Inventory entity if the inventory management needs to be more complex, such as tracking inventory across multiple locations or warehouses. This would involve defining a one-to-one or one-to-many relationship between the Product and Inventory entities, depending on the requirements.
//////////3. Product Reviews: I would create a separate Review entity class that represents product reviews. The Product class would have a one-to-many relationship with the Review class, allowing each product to have multiple reviews. The Review class would include fields such as 'rating', 'comment', and 'reviewerName' to capture the details of each review. This design would allow for easy retrieval of reviews for a specific product and enable features such as displaying average ratings or filtering products based on review criteria. Overall, this approach promotes modularity and separation of concerns while allowing for the expansion of product-related features in the application.
/////////This design allows for a flexible and scalable architecture, enabling the application to support additional features without significant changes to the existing codebase. By using JPA annotations to define relationships between entities, we can ensure that the database schema is properly structured to accommodate these new features while maintaining data integrity and performance.