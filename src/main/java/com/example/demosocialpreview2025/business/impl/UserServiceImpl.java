package com.example.demosocialpreview2025.business.impl;

import com.example.demosocialpreview2025.business.interfaces.UserService;
import com.example.demosocialpreview2025.domain.dto.UserDto;
import com.example.demosocialpreview2025.domain.request.SignUpRequest;
import com.example.demosocialpreview2025.domain.response.SignUpResponse;
import com.example.demosocialpreview2025.mapper.UserMapperDto;
import com.example.demosocialpreview2025.persistence.entity.Role;
import com.example.demosocialpreview2025.persistence.entity.UserEntity;
import com.example.demosocialpreview2025.persistence.repository.UserRepo;
import com.example.demosocialpreview2025.security.jwt.JwtUtils;
import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private final UserRepo userRepository;

    private final UserMapperDto userMapperDto;

    @Override
    public SignUpResponse createUser(SignUpRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {

            throw new RuntimeException("Username is already taken.");

        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {   // Optional
            throw new RuntimeException("Email is already taken.");
        }

        if(userRepository.existsByFullName(signUpRequest.getFullName())) {
            throw new RuntimeException("Full name is already taken.");
        }

        UserEntity user = userMapperDto.mapToEntity(signUpRequest);

        userRepository.save(user);

        // Optionally generate token on signup (if needed)
        return SignUpResponse.builder()
                .user(userMapperDto.mapToDTO(user))
                .message("User created successfully").build();

    }

    @Override
    public UserDto LoginRequest(SignUpRequest signUpRequest) {
        try {
            // Authenticate user credentials
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            signUpRequest.getUsername(),
                            signUpRequest.getPassword()
                    )
            );

            // Set authentication context
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Load user data
            UserEntity user = userRepository.findByUsername(signUpRequest.getUsername())
                    .orElseThrow(() -> new RuntimeException("User not found after authentication"));

            // Generate JWT token
            String jwtToken = jwtUtils.generateTokenFromUsername(signUpRequest.getUsername());

            // Convert to DTO and set token
            UserDto dto = UserDto.fromEntity(user);
            dto.setToken(jwtToken);

            return dto;
        } catch (Exception e) {
            throw new RuntimeException("Invalid username or password: " + e.getMessage());
        }
    }

    @Override
    public UserDto getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(UserDto::fromEntity)
                .orElse(null);
    }

    @Override
    public UserDto getUserById(Long id) {
        return userRepository.findById(id)
                .map(UserDto::fromEntity)
                .orElse(null);
    }

    @Override
    public UserDto updateUser(UserDto userDto) {
        Optional<UserEntity> optionalUser = userRepository.findById(userDto.getId());
        if (optionalUser.isEmpty()) {
            throw new RuntimeException("User not found with ID: " + userDto.getId());
        }

        UserEntity user = optionalUser.get();
        user.setEmail(userDto.getEmail());
        user.setFullName(userDto.getFullName());
        user.setUsername(userDto.getUsername());
        user.setImage(userDto.getImage());

        userRepository.save(user);

        return UserDto.fromEntity(user);
    }

    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found with ID: " + id);
        }
        userRepository.deleteById(id);
    }
}
