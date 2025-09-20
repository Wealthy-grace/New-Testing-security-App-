package com.example.demosocialpreview2025.domain.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LinkedinResponse {

    private Long id;
    private String message;
    private String url;
}
