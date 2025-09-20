package com.example.springsecuritydemo2025.business.interfaces;

import com.example.springsecuritydemo2025.domain.dto.UserDto;
import com.example.springsecuritydemo2025.domain.request.SignUpRequest;

public interface UserService {

    UserDto createUser(SignUpRequest signUpRequest);

    UserDto LoginRequest(SignUpRequest signUpRequest);

    UserDto getUserByUsername(String username);

    UserDto getUserById(Long id);

    UserDto updateUser(UserDto userDto);

    void deleteUser(Long id);
}
