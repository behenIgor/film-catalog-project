package com.ihorbehen.film_catalog.backend.repository;

import com.ihorbehen.film_catalog.backend.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}