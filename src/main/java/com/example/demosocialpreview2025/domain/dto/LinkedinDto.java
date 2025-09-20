package com.example.demosocialpreview2025.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class LinkedinDto {

    private  Long id;

    private String headline;

    private String title;

    private String description;

    private String image;

    private String url;
}
