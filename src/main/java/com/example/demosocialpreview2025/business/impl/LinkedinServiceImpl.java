package com.example.demosocialpreview2025.business.impl;

import com.example.demosocialpreview2025.business.interfaces.LinkedinService;
import com.example.demosocialpreview2025.configuration.exceptions.LinkedinAlreadyExistsException;
import com.example.demosocialpreview2025.domain.dto.LinkedinDto;
import com.example.demosocialpreview2025.domain.request.CreateLinkedinRequest;
import com.example.demosocialpreview2025.domain.response.LinkedinResponse;
import com.example.demosocialpreview2025.mapper.LinkedinMapperDto;
import com.example.demosocialpreview2025.persistence.entity.LinkedinEntity;
import com.example.demosocialpreview2025.persistence.repository.LinkedinRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
@Slf4j
@RequiredArgsConstructor
@Service
public class LinkedinServiceImpl implements LinkedinService {

    private final LinkedinRepo linkedinRepository;
    @Override
    public LinkedinResponse createLinkedin(CreateLinkedinRequest request) {

        // log info

        log.info("CreateLinkedinRequest: {}", request.getTitle());


        // checking if linkedin already exists
        CheckingLinkeidnExist(request);
        // mapping dto to entity
        LinkedinEntity linkedinEntity = LinkedinMapperDto.toEntity(request);
        // save entity
        linkedinRepository.save(linkedinEntity);
        // mapping entity to dto
        LinkedinDto linkedinDto = LinkedinMapperDto.MaptoDto(linkedinEntity);
        // return dto
        return LinkedinResponse.builder()
                .id(linkedinDto.getId())
                .url(linkedinDto.getUrl())
                .message("Linkedin created successfully")
                .build();
    }

    @Override
    public LinkedinResponse getLinkedin(Long id) {
        // get linkedin by id
        LinkedinEntity linkedinEntity = linkedinRepository.findById(id).get();
        // mapping entity to dto
        LinkedinDto linkedinDto = LinkedinMapperDto.MaptoDto(linkedinEntity);
        // return dto
        return LinkedinResponse.builder()
                .id(linkedinDto.getId())
                .url(linkedinDto.getUrl())
                .message("Linkedin found successfully")
                .build()
        ;
    }

    @Override
    public void deleteLinkedin(Long id) {

        if(!linkedinRepository.existsById(id)) {
            throw new LinkedinAlreadyExistsException("Linkedin not found" + id);
        }

        linkedinRepository.deleteById(id);

    }

    @Override
    public LinkedinDto updateLinkedin(Long id, CreateLinkedinRequest request) {
        LinkedinEntity linkedin = linkedinRepository.findById(id).get();
        if(!linkedinRepository.existsById(id)) {
            throw new LinkedinAlreadyExistsException("Linkedin not found" + id);
        }

        // update linkedin fields

        linkedin.setHeadline(request.getHeadline());
        linkedin.setTitle(request.getTitle());
        linkedin.setDescription(request.getDescription());
        linkedin.setImage(request.getImage());
        linkedin.setUrl(request.getUrl());

        LinkedinEntity updatedLinkedin = linkedinRepository.save(linkedin);

        return LinkedinMapperDto.MaptoDto(updatedLinkedin);

    }

    @Override
    public List<LinkedinDto> getAllLinkedin() {

       return linkedinRepository.findAll()
               .stream()
               .map(LinkedinMapperDto::MaptoDto)
               .toList();



    }

    private void CheckingLinkeidnExist(CreateLinkedinRequest request) {

        if(linkedinRepository.existsByHeadline(request.getHeadline())){
            throw new LinkedinAlreadyExistsException("Linkedin already exists" + request.getHeadline());
        }
        if(linkedinRepository.existsByTitle(request.getTitle())) {

            throw new LinkedinAlreadyExistsException("Linkedin already exists" + request.getTitle());
        }
        if(linkedinRepository.existsByDescription(request.getDescription())) {
            throw new LinkedinAlreadyExistsException("Linkedin already exists" + request.getDescription());

        }
    }
}
