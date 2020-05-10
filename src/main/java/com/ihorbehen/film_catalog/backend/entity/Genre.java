package com.ihorbehen.film_catalog.backend.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.LinkedList;
import java.util.List;

@Entity
public class Genre extends AbstractEntity {
    private String name;

    @OneToMany(mappedBy = "genre", fetch = FetchType.EAGER)
    private List<Film> films = new LinkedList<>();

    public Genre() {
    }

    public Genre(String name) {
        setName(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Film> getFilms() {
        return films;
    }
}
