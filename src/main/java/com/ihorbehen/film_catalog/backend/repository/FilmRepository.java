package com.ihorbehen.film_catalog.backend.repository;

import com.ihorbehen.film_catalog.backend.entity.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FilmRepository extends JpaRepository<Film, Long> {
    @Query("select c from Film c " +
            "where lower(c.titleUKR) like lower(concat('%', :searchTerm, '%')) " +
            "or lower(c.titleOriginal) like lower(concat('%', :searchTerm, '%'))") //
    List<Film> search(@Param("searchTerm") String searchTerm); //
}
