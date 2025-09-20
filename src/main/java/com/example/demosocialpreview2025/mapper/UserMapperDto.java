package com.example.demosocialpreview2025.mapper;


import com.example.demosocialpreview2025.domain.dto.UserDto;
import com.example.demosocialpreview2025.domain.request.SignUpRequest;
import com.example.demosocialpreview2025.persistence.entity.Role;
import com.example.demosocialpreview2025.persistence.entity.UserEntity;
import com.example.demosocialpreview2025.security.jwt.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public  class UserMapperDto {

    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    public  UserDto mapToDTO(UserEntity entity) {
        return UserDto.builder()
                .id(entity.getId())
                .fullName(entity.getFullName())
                .username(entity.getUsername())
                .email(entity.getEmail())
                .role(String.valueOf(entity.getRole()))
                .image(entity.getImage())
                .token(jwtUtils.generateTokenFromUsername(entity.getUsername()))
                .build();
    }


    public UserEntity mapToEntity(SignUpRequest request) {
        return UserEntity.builder()
                .fullName(request.getFullName())
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ROLE_USER)
                .image(request.getImage())
                .build();
    }
}
