package com.ihorbehen.film_catalog.backend.entity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
public class Film extends AbstractEntity implements Cloneable {

    public enum Rating {
        ONE, TWO, THREE, FOUR, FIVE
    }

    @NotNull
    @NotEmpty
    private String titleUKR = "";

    @NotNull
    @NotEmpty
    private String titleOriginal = "";

    @ManyToOne
    @JoinColumn(name = "genre_id")
    private Genre genre;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Film.Rating rating;


    @NotNull
    @NotEmpty
    private String year = "";

    @NotNull
    private String  comment = "";

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public String getTitleOriginal() {
        return titleOriginal;
    }

    public void setTitleOriginal(String titleOriginal) {
        this.titleOriginal = titleOriginal;
    }

    public String getTitleUKR() {
        return titleUKR;
    }

    public void setTitleUKR(String titleUKR) {
        this.titleUKR = titleUKR;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public Genre getGenre() {
        return genre;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return titleUKR + " " + titleOriginal;
    }

}