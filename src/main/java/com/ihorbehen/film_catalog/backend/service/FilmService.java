package com.ihorbehen.film_catalog.backend.service;

import com.ihorbehen.film_catalog.backend.entity.Genre;
import com.ihorbehen.film_catalog.backend.entity.Film;
import com.ihorbehen.film_catalog.backend.repository.GenreRepository;
import com.ihorbehen.film_catalog.backend.repository.FilmRepository;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class FilmService {
    private static final Logger LOGGER = Logger.getLogger(FilmService.class.getName());
    private FilmRepository filmRepository;
    private GenreRepository genreRepository;

    public FilmService(FilmRepository filmRepository,
                       GenreRepository genreRepository) {
        this.filmRepository = filmRepository;
        this.genreRepository = genreRepository;
    }

    public List<Film> findAll() {
        return filmRepository.findAll();
    }

    public List<Film> findAll(String stringFilter) {
        if (stringFilter == null || stringFilter.isEmpty()) {
            return filmRepository.findAll();
        } else {
            return filmRepository.search(stringFilter);
        }
    }

    public long count() {
        return filmRepository.count();
    }

    public void delete(Film film) {
        filmRepository.delete(film);
    }

    public void save(Film film) {
        if (film == null) {
            LOGGER.log(Level.SEVERE,
                    "Film is null. Are you sure you have connected your form to the application?");
            return;
        }
        filmRepository.save(film);
    }

    @PostConstruct
    public void populateTestData() {
        if (genreRepository.count() == 0) {
            genreRepository.saveAll(
                    Stream.of("Thriller", "Sci-Fi", "Comedy", "Detective", "Fantasy", "Mystic", "Horror", "Drama")
                            .map(Genre::new)
                            .collect(Collectors.toList()));
        }

        if (filmRepository.count() == 0) {
            Random r = new Random(0);
            List<Genre> genres = genreRepository.findAll();
            filmRepository.saveAll(
                    Stream.of("Агенти А.Н.К.Л. / The Man from U.N.C.L.E. / 2015",
                            "Американський снайпер / American Sniper / 2014",
                            "Апгрейд / Upgrade / 2018",
                            "Багно / Filth / 2013",
                            "Безмежний розум: Область темряви / Limitless / 2011",
                            "Безсоння / Insomnia / 2002",
                            "Бердмен / Birdman: The Unexpected Virtue of Ignorance / 2014",
                            "Близькі контакти третього ступеня / Close Encounters of the Third Kind / 1977",
                            "Бойовий гіпноз проти кіз / The Men Who Stare at Goats / 2009",
                            "Будь-якою ціною / Hell or High Water / 2016")
                            .map(name -> {
                                String[] split = name.split(" / ");
                                Film film = new Film();
                                film.setTitleUKR(split[0]);
                                film.setTitleOriginal(split[1]);
                                film.setYear(split[2]);
                                film.setGenre(genres.get(r.nextInt(genres.size())));
                                film.setRating(Film.Rating.values()[r.nextInt(Film.Rating.values().length)]);
                                return film;
                            }).collect(Collectors.toList()));
        }
    }
}
