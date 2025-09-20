package com.example.springsecuritydemo2025.controller;

import com.example.springsecuritydemo2025.business.interfaces.UserService;
import com.example.springsecuritydemo2025.domain.dto.UserDto;
import com.example.springsecuritydemo2025.domain.request.SignUpRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpRequest request) {
        try {
            UserDto createdUser = userService.createUser(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                    new ErrorResponse("Signup failed", e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody SignUpRequest request) {
        try {
            UserDto dto = userService.LoginRequest(request);
            return ResponseEntity.ok(dto);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    new ErrorResponse("Login failed", e.getMessage()));
        }
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<?> getUser(@PathVariable String username) {
        UserDto user = userService.getUserByUsername(username);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ErrorResponse("User not found", "No user with username: " + username));
        }
        return ResponseEntity.ok(user);
    }

    // Optional: error response inner class
    record ErrorResponse(String error, String message) {}


    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/user")
    public String userEndpoint(){
        return "Hello, User!";
    }

}
