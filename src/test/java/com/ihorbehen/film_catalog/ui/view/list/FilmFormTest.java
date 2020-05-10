package com.ihorbehen.film_catalog.ui.view.list;

import com.ihorbehen.film_catalog.backend.entity.Genre;
import com.ihorbehen.film_catalog.backend.entity.Film;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class FilmFormTest {
    private List<Genre> genres;
    private Film enemy;
    private Genre genre1;
    private Genre genre2;

    @Before
    public void setupData() {
        genres = new ArrayList<>();
        genre1 = new Genre("Thriller");
        genre2 = new Genre("Sci-Fi");
        genres.add(genre1);
        genres.add(genre2);

        enemy = new Film();
        enemy.setTitleUKR("Ворог");
        enemy.setTitleOriginal("Enemy");
        enemy.setYear("2013");
        enemy.setRating(Film.Rating.TWO);
        enemy.setGenre(genre2);
    }

    @Test
    public void formFieldsPopulated() {
        FilmForm form = new FilmForm(genres);
        form.setFilm(enemy);
        Assert.assertEquals("Ворог", form.titleUkr.getValue());
        Assert.assertEquals("Enemy", form.titleOriginal.getValue());
        Assert.assertEquals("2013", form.year.getValue());
        Assert.assertEquals(genre2, form.genre.getValue());
        Assert.assertEquals(Film.Rating.TWO, form.rating.getValue());
    }

    @Test
    public void saveEventHasCorrectValues() {
        FilmForm form = new FilmForm(genres);
        Film film = new Film();
        form.setFilm(film);

        form.titleUkr.setValue("Детектив");
        form.titleOriginal.setValue("Sleuth");
        form.genre.setValue(genre1);
        form.year.setValue("2007");
        form.rating.setValue(Film.Rating.FOUR);

        AtomicReference<Film> savedContactRef = new AtomicReference<>(null);
        form.addListener(FilmForm.SaveEvent.class, e -> {
            savedContactRef.set(e.getFilm());
        });
        form.save.click();
        Film savedFilm = savedContactRef.get();

        Assert.assertEquals("Детектив", savedFilm.getTitleUKR());
        Assert.assertEquals("Sleuth", savedFilm.getTitleOriginal());
        Assert.assertEquals("2007", savedFilm.getYear());
        Assert.assertEquals(genre1, savedFilm.getGenre());
        Assert.assertEquals(Film.Rating.FOUR, savedFilm.getRating());
    }

}