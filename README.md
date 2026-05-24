E-Commerce App — Auth & Product End-to-End Guide
=================================================

This README explains the authentication (JWT + Spring Security) and product CRUD flows in this project, how the pieces fit together, and how to test them end-to-end (Postman). It's written so you can understand what happens behind the scenes.

Contents
- Overview
- Requirements & quick start
- Configuration / properties
- Authentication (concepts + implementation)
  - Login endpoint
  - JwtUtil
  - JwtAuthenticationFilter
  - SecurityConfig
  - User store used for testing
- Product flow (concepts + implementation)
  - Controller, DTOs, Service, Repository, Model
  - How a request is processed
- How to test with Postman (step-by-step)
- Troubleshooting & common issues
- Next steps / improvements

Overview
--------
This Spring Boot application exposes:
- /auth/login — accepts credentials, returns a JWT token
- /products/** — protected CRUD endpoints for products (requires Bearer token)

We use:
- Spring Boot (Web, JPA)
- Spring Security for securing endpoints
- JWT (io.jsonwebtoken / jjwt) for stateless auth tokens
- PostgreSQL via Spring Data JPA for persistence

Requirements & quick start
--------------------------
Prereqs:
- Java 17
- Maven
- PostgreSQL (or you can switch to an in-memory DB for quick testing)

Create DB (Postgres):
```bash
createdb e_com
```

Build and run:
```bash
mvn clean package
mvn spring-boot:run
```
App listens on port from `src/main/resources/application.properties` (default in repo: 8082).

Configuration / properties
--------------------------
Key properties (in `src/main/resources/application.properties`):
- `spring.datasource.*` — JDBC URL, username, password
- `spring.jpa.hibernate.ddl-auto` — how JPA manages schema (e.g., `update`)
- `jwt.secret` — the HMAC secret used to sign tokens (must be long and kept secret in production)
- `jwt.expiration-ms` — token expiry in milliseconds

There is an `application-prod.properties` with `springdoc.swagger-ui.enabled=false` to disable Swagger UI in prod.

Authentication — concepts + implementation
-----------------------------------------
Concepts (brief):
- JWT (JSON Web Token): signed token containing claims (we use `sub` for username). The server signs tokens with a secret; clients present tokens with `Authorization: Bearer <token>`.
- Spring Security: framework to authenticate and authorize requests. We configure a filter that extracts and validates JWTs and then sets the authenticated principal into `SecurityContext`.
- Stateless auth: Server does not keep session state — it trusts a valid JWT.

Implementation files (where to look):
- `src/main/java/com/example/com/e_com/controller/AuthController.java` — `POST /auth/login`.
- `src/main/java/com/example/com/e_com/util/JwtUtil.java` — generates and validates tokens using `jwt.secret`.
- `src/main/java/com/example/com/e_com/filter/JwtAuthenticationFilter.java` — `OncePerRequestFilter` that reads Authorization header, validates token, and sets authentication.
- `src/main/java/com/example/com/e_com/config/SecurityConfig.java` — configures `UserDetailsService`, `PasswordEncoder`, `SecurityFilterChain`, exposes `AuthenticationManager` and wires the JWT filter.

Login endpoint
- URL: `POST /auth/login` (public)
- Request JSON: `{ "username": "user", "password": "password" }`
- The controller calls `AuthenticationManager.authenticate(...)`. If credentials are valid, it calls `JwtUtil.generateToken(username)` and returns `{ "token": "..." }`.

JwtUtil (what it does)
- Reads `jwt.secret` and `jwt.expiration-ms` from properties
- `generateToken(username)` builds a signed JWT with subject=username and expiration
- `validateToken(token)` parses and verifies signature and expiry
- `getUsername(token)` extracts the subject from token

JwtAuthenticationFilter (what it does)
- Runs once per request
- Reads `Authorization` header, extracts `Bearer ` token
- Validates token via `JwtUtil` — if valid, gets username and loads `UserDetails` from `UserDetailsService`
- Creates `UsernamePasswordAuthenticationToken` and sets it into `SecurityContextHolder`
- Allows request to continue; downstream controllers see an authenticated principal

SecurityConfig (key points)
- Declares a `UserDetailsService` (currently an in-memory user for testing: `user` / `password`)
- Declares a `PasswordEncoder` (BCrypt)
- `SecurityFilterChain` permits `/auth/**` and `swagger` endpoints; protects all others — adds the `JwtAuthenticationFilter` before username-password filter
- Exposes an `AuthenticationManager` bean used by the login controller

Product flow (concepts + implementation)
----------------------------------------
Files:
- `ProductController` — REST endpoints: GET /products, POST /products, GET /products/{id}, PUT /products/{id}, DELETE /products/{id}
- `ProductRequest` / `ProductResponse` DTOs — incoming/outgoing payloads
- `ProductService` / `ProductServiceImpl` — business logic and mapping between DTO and `Product` entity
- `ProductRepository` — Spring Data JPA repository for `Product` entity
- `Product` — JPA entity mapped to `products` table

How a protected request is processed (example: create product)
1. Client calls `POST /products` with JSON and header `Authorization: Bearer <token>`
2. `JwtAuthenticationFilter` runs, validates token and sets SecurityContext
3. Spring Security allows the request (authentication present)
4. `ProductController.createProduct` receives DTO, calls `ProductService.createProduct`
5. Service maps DTO to `Product` entity and calls `ProductRepository.save`
6. JPA persists the entity, controller returns `201 Created` with `ProductResponse`

How to test with Postman (step-by-step)
----------------------------------------
1) Login to get token
- POST `http://localhost:8082/auth/login`
- Body JSON: `{ "username": "user", "password": "password" }`
- Response: `{ "token": "<JWT>" }`

2) Save the token as an environment variable in Postman (Tests tab of login request):
```javascript
pm.environment.set("jwt_token", pm.response.json().token);
```

3) Use token in subsequent requests
- Header: `Authorization: Bearer {{jwt_token}}`

4) Create a product
- POST `http://localhost:8082/products`
- Headers: `Authorization: Bearer {{jwt_token}}`, `Content-Type: application/json`
- Body:
```json
{ "name": "Laptop", "description": "Fast laptop", "price": 1299.99 }
```
- Expect `201 Created` with created product JSON

5) List products
- GET `http://localhost:8082/products`
- Expect `200 OK` with array

6) Get / Update / Delete by ID similarly

Troubleshooting
---------------
- 401 Unauthorized from product endpoints: check token, ensure you used `Authorization: Bearer <token>`.
- 500 on startup related to DB: ensure Postgres is running and `e_com` DB exists, or switch to an in-memory DB for quick tests.
  - Create DB: `createdb e_com` or change `spring.datasource.url` to H2 for local testing.
- JWT issues: ensure `jwt.secret` is set and long enough; tokens expire according to `jwt.expiration-ms`.

Security notes / production checklist
------------------------------------
- Do NOT use the in-memory user in production. Implement `UserDetailsService` backed by your `User` entity and `UserRepository` and secure password storage (BCrypt hashes).
- Store `jwt.secret` outside VCS — environment variable, Vault, or a Kubernetes secret.
- Consider short-lived access tokens and refresh tokens.
- Enable HTTPS in production to protect Authorization header in transit.

Next steps / improvements
-------------------------
- Replace in-memory user with a `User` entity, `UserRepository`, registration flow with password hashing.
- Add role-based authorization on product endpoints (e.g., admin-only create/update/delete).
- Add token refresh and logout (token blacklist or short expiry + refresh token).
- Add integration tests for auth and product endpoints.

If you want, I can:
- Add Postman collection file (exported) for you to import and run tests quickly.
- Replace the in-memory user with a real `User`+`Repository` implementation and register endpoint.

---
File references (where to look):
- `src/main/java/com/example/com/e_com/controller/AuthController.java`
- `src/main/java/com/example/com/e_com/util/JwtUtil.java`
- `src/main/java/com/example/com/e_com/filter/JwtAuthenticationFilter.java`
- `src/main/java/com/example/com/e_com/config/SecurityConfig.java`
- `src/main/java/com/example/com/e_com/controller/ProductController.java`
- `src/main/java/com/example/com/e_com/service/ProductServiceImpl.java`
- `src/main/java/com/example/com/e_com/repository/ProductRepository.java`
- `src/main/resources/application.properties`

Happy to add the Postman collection and a short demo script next — want me to generate those?
# E-Commerce-SpringBoot-Web-App