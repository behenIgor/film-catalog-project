package com.ihorbehen.film_catalog.ui.view.dashboard;

import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.ihorbehen.film_catalog.backend.service.GenreService;
import com.ihorbehen.film_catalog.backend.service.FilmService;
import com.ihorbehen.film_catalog.ui.MainLayout;

@Route(value = "dashboard", layout = MainLayout.class)
@PageTitle("Dashboard | Films Catalog")
public class DashboardView extends VerticalLayout {

    private FilmService filmService;
    private GenreService genreService;

    public DashboardView(FilmService filmService, GenreService genreService) {
        this.filmService = filmService;
        this.genreService = genreService;
        addClassName("dashboard-view");
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);

        add(getFilmStats()/*, getFilmsChart()*/);
    }

    private Span getFilmStats() {
        Span stats = new Span(filmService.count() + " films");
        stats.addClassName("films-stats");
        return stats;
    }

//    private Chart getFilmsChart() {
//        Chart chart = new Chart(ChartType.PIE);
//
//        DataSeries dataSeries = new DataSeries();
//        Map<String, Integer> genres = genreService.getStats();
//        genres.forEach((genre, films) ->
//                dataSeries.add(new DataSeriesItem(genre, films)));
//        chart.getConfiguration().setSeries(dataSeries);
//        return chart;
//    }
}