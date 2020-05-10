package com.ihorbehen.film_catalog.backend.service;

import com.ihorbehen.film_catalog.backend.entity.Genre;
import com.ihorbehen.film_catalog.backend.repository.GenreRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GenreService {

    private GenreRepository genreRepository;

    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public List<Genre> findAll() {
        return genreRepository.findAll();
    }

    public Map<String, Integer> getStats() {
        HashMap<String, Integer> stats = new HashMap<>();
        findAll().forEach(genre -> stats.put(genre.getName(), genre.getFilms().size()));
        return stats;
    }
}