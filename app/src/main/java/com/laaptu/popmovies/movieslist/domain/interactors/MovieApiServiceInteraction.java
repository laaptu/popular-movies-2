package com.laaptu.popmovies.movieslist.domain.interactors;

import com.laaptu.popmovies.models.Movie;
import com.laaptu.popmovies.network.MovieApiService;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class MovieApiServiceInteraction {

    private MovieApiService movieApiService;

    @Inject
    public MovieApiServiceInteraction(MovieApiService movieApiService) {
        this.movieApiService = movieApiService;
    }

    public Single<List<Movie>> getPopularMovies() {
        return movieApiService.getPopularMovies().map(moviesList -> moviesList.movies);
    }

    public Single<List<Movie>> getTopRatedMovies() {
        return movieApiService.getTopRatedMovies().map(moviesList -> moviesList.movies);
    }
}
