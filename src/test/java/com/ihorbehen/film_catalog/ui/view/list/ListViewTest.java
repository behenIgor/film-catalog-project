package com.ihorbehen.film_catalog.ui.view.list;

import com.ihorbehen.film_catalog.backend.entity.Film;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.data.provider.ListDataProvider;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ListViewTest {

    @Autowired
    private ListView listView;

    @Test
    public void formShownWhenContactSelected() {
        Grid<Film> grid = listView.grid;
        Film firstFilm = getFirstItem(grid);

        FilmForm form = listView.form;

        Assert.assertFalse(form.isVisible());
        grid.asSingleSelect().setValue(firstFilm);
        Assert.assertTrue(form.isVisible());
        Assert.assertEquals(firstFilm, form.binder.getBean());
    }
    private Film getFirstItem(Grid<Film> grid) {
        return( (ListDataProvider<Film>) grid.getDataProvider()).getItems().iterator().next();
    }
}