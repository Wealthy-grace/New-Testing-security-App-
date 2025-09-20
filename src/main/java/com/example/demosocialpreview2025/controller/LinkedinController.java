package com.example.demosocialpreview2025.controller;


import com.example.demosocialpreview2025.business.interfaces.LinkedinService;
import com.example.demosocialpreview2025.configuration.exceptions.LinkedinAlreadyExistsException;
import com.example.demosocialpreview2025.domain.dto.LinkedinDto;
import com.example.demosocialpreview2025.domain.request.CreateLinkedinRequest;
import com.example.demosocialpreview2025.domain.response.LinkedinResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/linkedin")
@RestController
public class LinkedinController {

    private final LinkedinService linkedinService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
   public ResponseEntity<LinkedinResponse> createLinkedin(@Valid @RequestBody CreateLinkedinRequest request) {

        try {
            LinkedinResponse linkedinResponse = linkedinService.createLinkedin(request);
            return new ResponseEntity<>(linkedinResponse, HttpStatus.CREATED);
        } catch (LinkedinAlreadyExistsException e) {
            return new ResponseEntity<>(
                    LinkedinResponse.builder()
                            .id(null)
                            .message(e.getMessage())
                            .build(),
                    HttpStatus.BAD_REQUEST
            );

        }

   }

   @GetMapping("/{id}")
   public ResponseEntity<LinkedinResponse> getLinkedin(@PathVariable Long id) {
       LinkedinResponse linkedinResponse = linkedinService.getLinkedin(id);
       return new ResponseEntity<>(linkedinResponse, HttpStatus.OK);
   }

   @GetMapping
   public ResponseEntity<List<LinkedinDto>> getAllLinkedin() {
       return new ResponseEntity<>(linkedinService.getAllLinkedin(), HttpStatus.OK);
   }


   @PreAuthorize("hasRole('ROLE_ADMIN')")
   @PutMapping("/{id}")
   public ResponseEntity<LinkedinDto> updateLinkedin(@PathVariable Long id, @Valid @RequestBody CreateLinkedinRequest request) {
       return new ResponseEntity<>(linkedinService.updateLinkedin(id, request), HttpStatus.OK);
   }

   @PreAuthorize("hasRole('ROLE_ADMIN')")
   @DeleteMapping("/{id}")
   public ResponseEntity<LinkedinResponse> deleteLinkedin(@PathVariable Long id) {
       linkedinService.deleteLinkedin(id);
       return new ResponseEntity<>(HttpStatus.OK);
   }
}
