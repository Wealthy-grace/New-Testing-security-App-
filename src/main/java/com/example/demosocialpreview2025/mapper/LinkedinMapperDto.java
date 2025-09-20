package com.example.demosocialpreview2025.mapper;

import com.example.demosocialpreview2025.domain.dto.LinkedinDto;
import com.example.demosocialpreview2025.domain.request.CreateLinkedinRequest;
import com.example.demosocialpreview2025.persistence.entity.LinkedinEntity;

public class LinkedinMapperDto {

    public static LinkedinDto MaptoDto(LinkedinEntity entity) {
        return LinkedinDto.builder()
                .id(entity.getId())
                .headline(entity.getHeadline())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .image(entity.getImage())
                .url(entity.getUrl())
                .build();
    }

    public static LinkedinEntity toEntity(CreateLinkedinRequest request) {
        return LinkedinEntity.builder()
                .headline(request.getHeadline())
                .title(request.getTitle())
                .description(request.getDescription())
                .image(request.getImage())
                .url(request.getUrl())
                .build();
    }
}
