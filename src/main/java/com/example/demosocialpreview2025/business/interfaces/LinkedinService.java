package com.example.demosocialpreview2025.business.interfaces;

import com.example.demosocialpreview2025.domain.dto.LinkedinDto;
import com.example.demosocialpreview2025.domain.request.CreateLinkedinRequest;
import com.example.demosocialpreview2025.domain.response.LinkedinResponse;

import java.util.List;

public interface LinkedinService {
    LinkedinResponse createLinkedin(CreateLinkedinRequest request);

    LinkedinResponse getLinkedin(Long id);

    void deleteLinkedin(Long id);

    LinkedinDto updateLinkedin(Long id, CreateLinkedinRequest request);

    List<LinkedinDto> getAllLinkedin();
}
