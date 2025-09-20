package com.example.springsecuritydemo2025.business.impl;

import com.example.springsecuritydemo2025.business.interfaces.UserService;
import com.example.springsecuritydemo2025.domain.dto.UserDto;
import com.example.springsecuritydemo2025.domain.request.SignUpRequest;
import com.example.springsecuritydemo2025.persistence.entity.Role;
import com.example.springsecuritydemo2025.persistence.entity.UserEntity;
import com.example.springsecuritydemo2025.persistence.repository.UserRepo;
import com.example.springsecuritydemo2025.security.jwt.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private final UserRepo userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDto createUser(SignUpRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            throw new RuntimeException("Username is already taken.");
        }

        UserEntity user = new UserEntity();
        user.setUsername(signUpRequest.getUsername());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        user.setEmail(signUpRequest.getEmail());
        user.setFullName(signUpRequest.getFullName());
        user.setEnabled(true);

        //user.setRole(signUpRequest.getRole());
        user.setRole(Role.ROLE_USER);
        user.setImage(signUpRequest.getImage());

        userRepository.save(user);

        // Optionally generate token on signup (if needed)
        UserDto dto = UserDto.fromEntity(user);
        // dto.setToken(jwtUtils.generateTokenFromUsername(user.getUsername())); // Optional

        return dto;
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
