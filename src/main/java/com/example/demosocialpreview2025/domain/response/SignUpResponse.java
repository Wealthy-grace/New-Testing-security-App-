package com.example.demosocialpreview2025.domain.response;


import com.example.demosocialpreview2025.domain.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SignUpResponse {

    private String message;

    private UserDto user;


}
