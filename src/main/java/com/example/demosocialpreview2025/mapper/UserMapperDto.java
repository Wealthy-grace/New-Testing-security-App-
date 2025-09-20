package com.example.springsecuritydemo2025.business.Converter;

import com.example.springsecuritydemo2025.domain.dto.BookDto;
import com.example.springsecuritydemo2025.domain.dto.UserDto;
import com.example.springsecuritydemo2025.persistence.entity.BookEntity;
import com.example.springsecuritydemo2025.persistence.entity.Role;
import com.example.springsecuritydemo2025.persistence.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserConverDto {

    private UserDto mapToDTO(UserEntity entity) {
        return UserDto.builder()
                .id(entity.getId())
                .fullName(entity.getFullName())
                .username(entity.getUsername())
                .email(entity.getEmail())
                .role(String.valueOf(entity.getRole()))
                .image(entity.getImage())
                .build();
    }


    private UserEntity mapToEntity(UserDto dto) {
        return UserEntity.builder()
                .id(dto.getId())
                .fullName(dto.getFullName())
                .username(dto.getUsername())
                .email(dto.getEmail())
                .role(Role.ROLE_ADMIN)
                //.role(Role.valueOf(dto.getRole()))
                .image(dto.getImage())
                .build();
    }
}
