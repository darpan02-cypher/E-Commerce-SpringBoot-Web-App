package com.example.com.e_com.service ;
import org.springframework.stereotype.Service;

@Service    

public class AuthService {
    public String authenticate(String username, String password) {
        // Implement authentication logic (e.g., check against database)
        // For simplicity, we return a dummy token
        return "dummy-jwt-token-for-" + username;
    }

    public void register(String username, String password) {
        // Implement user registration logic (e.g., save to database)
    }

    public boolean validateToken(String token) {
        // Implement token validation logic (e.g., check signature, expiration)
        return token.startsWith("dummy-jwt-token-for-");
    }
    
}
