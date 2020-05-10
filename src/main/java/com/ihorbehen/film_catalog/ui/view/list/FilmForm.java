package com.ihorbehen.film_catalog.ui.view.list;

import com.ihorbehen.film_catalog.backend.entity.Film;
import com.ihorbehen.film_catalog.backend.entity.Genre;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.shared.Registration;

import java.util.List;

public class FilmForm extends FormLayout {

    TextField titleUkr = new TextField("Title UKR");
    TextField titleOriginal = new TextField("Title Original");
    TextField year = new TextField("Year");
    ComboBox<Film.Rating> rating = new ComboBox<>("Rating");
    ComboBox<Genre> genre = new ComboBox<>("Genre");
    TextField comment = new TextField("Comment");

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button close = new Button("Cancel");

    Binder<Film> binder = new BeanValidationBinder<>(Film.class);

    public FilmForm(List<Genre> genres) {
        addClassName("film-form");

        binder.bindInstanceFields(this);
        rating.setItems(Film.Rating.values());
        genre.setItems(genres);
        genre.setItemLabelGenerator(Genre::getName);

        add(
                titleUkr,
                titleOriginal,
                year,
                rating,
                genre,
                comment,
                createButtonsLayout()
        );
    }

    public void setFilm(Film film) {
        binder.setBean(film);
    }

    private Component createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(click -> validateAndSave());
        delete.addClickListener(click -> fireEvent(new DeleteEvent(this, binder.getBean())));
        close.addClickListener(click -> fireEvent(new CloseEvent(this)));

        binder.addStatusChangeListener(evt -> save.setEnabled(binder.isValid()));

        return new HorizontalLayout(save, delete, close);
    }

    private void validateAndSave() {
        if (binder.isValid()) {
            fireEvent(new SaveEvent(this, binder.getBean()));
        }
    }

    // Events
    public static abstract class FilmFormEvent extends ComponentEvent<FilmForm> {
        private Film film;

        protected FilmFormEvent(FilmForm source, Film film) {
            super(source, false);
            this.film = film;
        }

        public Film getFilm() {
            return film;
        }
    }

    public static class SaveEvent extends FilmFormEvent {
        SaveEvent(FilmForm source, Film film) {
            super(source, film);
        }
    }

    public static class DeleteEvent extends FilmFormEvent {
        DeleteEvent(FilmForm source, Film film) {
            super(source, film);
        }

    }

    public static class CloseEvent extends FilmFormEvent {
        CloseEvent(FilmForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}