package com.example.demosocialpreview2025.domain.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateLinkedinRequest {

    @NotBlank(message = "Headline is required")
    @Size(max = 255, message = "Headline cannot exceed 255 characters")
    private String headline;

    @NotBlank(message = "Title is required")
    @Size(max = 255, message = "Title cannot exceed 255 characters")
    private String title;

    @NotBlank(message = "Description is required")
    @Size(max = 255, message = "Description cannot exceed 255 characters")
    private String description;

    @NotBlank(message = "Image is required")
    @Size(max = 255, message = "Image cannot exceed 255 characters")
    private String image;

    @NotBlank(message = "Url is required")
    @Size(max = 255, message = "Url cannot exceed 255 characters")
    private String url;
}
