package com.ihorbehen.film_catalog.ui.view.list;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.ihorbehen.film_catalog.backend.entity.Genre;
import com.ihorbehen.film_catalog.backend.entity.Film;
import com.ihorbehen.film_catalog.backend.service.GenreService;
import com.ihorbehen.film_catalog.backend.service.FilmService;
import com.ihorbehen.film_catalog.ui.MainLayout;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
@Route(value = "", layout = MainLayout.class)
@PageTitle("Films | Catalog")
public class ListView extends VerticalLayout {
    FilmService filmService;

    Grid<Film> grid = new Grid<>(Film.class);
    TextField filterText = new TextField();
    FilmForm form;

    public ListView(FilmService filmService,
                    GenreService genreService) {
        this.filmService = filmService;
        addClassName("list-view");
        setSizeFull();

        configureGrid();

        form = new FilmForm(genreService.findAll());

        form.addListener(FilmForm.SaveEvent.class, this::saveFilm);
        form.addListener(FilmForm.DeleteEvent.class, this::deleteFilm);
        form.addListener(FilmForm.CloseEvent.class, e -> closeEditor());

        Div content = new Div(grid, form);
        content.addClassName("content");
        content.setSizeFull();

        add(getToolbar(), content);
        updateList();
        closeEditor();

    }

    private HorizontalLayout getToolbar() {
        filterText.setPlaceholder("Filter by name...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button addContactButton = new Button("Add Film");
        addContactButton.addClickListener(click -> addFilm());

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addContactButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    void addFilm() {
        grid.asSingleSelect().clear();
        editFilm(new Film());
    }

    private void configureGrid() {
        grid.addClassName("film-grid");
        grid.setSizeFull();
        grid.removeColumnByKey("titleUKR");
        grid.setColumns("titleUKR", "titleOriginal", "year", "rating");
        grid.addColumn(film -> {
            Genre genre = film.getGenre();
            return genre == null ? "-" : genre.getName();
        }).setHeader("Genre");
        grid.addColumn("comment");

        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        grid.asSingleSelect().addValueChangeListener(event ->
                editFilm(event.getValue()));
    }

    private void updateList() {
        grid.setItems(filmService.findAll(filterText.getValue()));
    }

    public void editFilm(Film film) {
        if (film == null) {
            closeEditor();
        } else {
            form.setFilm(film);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void closeEditor() {
        form.setFilm(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void saveFilm(FilmForm.SaveEvent event) {
        filmService.save(event.getFilm());
        updateList();
        closeEditor();
    }

    private void deleteFilm(FilmForm.DeleteEvent event) {
        filmService.delete(event.getFilm());
        updateList();
        closeEditor();
    }
}