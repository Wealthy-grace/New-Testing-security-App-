package com.example.demosocialpreview2025.persistence.repository;

import com.example.demosocialpreview2025.persistence.entity.LinkedinEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LinkedinRepo extends JpaRepository<LinkedinEntity, Long> {


    Optional<LinkedinEntity> findById(Long aLong);

    boolean existsByUrl(String url);

    boolean existsByTitle(String title);

    boolean existsByDescription(String description);

    boolean existsByImage(String image);

    boolean existsByHeadline(String headline);

}
