package com.example.demosocialpreview2025.business.interfaces;


import com.example.demosocialpreview2025.domain.dto.UserDto;
import com.example.demosocialpreview2025.domain.request.SignUpRequest;
import com.example.demosocialpreview2025.domain.response.SignUpResponse;

public interface UserService {

    SignUpResponse createUser(SignUpRequest signUpRequest);

    UserDto LoginRequest(SignUpRequest signUpRequest);

    UserDto getUserByUsername(String username);

    UserDto getUserById(Long id);

    UserDto updateUser(UserDto userDto);

    void deleteUser(Long id);
}
