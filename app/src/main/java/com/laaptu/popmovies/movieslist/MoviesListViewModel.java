package com.laaptu.popmovies.movieslist;

import com.laaptu.popmovies.network.MovieApiService;

import javax.inject.Inject;

import io.reactivex.schedulers.Schedulers;

public class MoviesListViewModel {

    private MovieApiService movieApiService;

    @Inject
    public MoviesListViewModel(MovieApiService movieApiService) {
        this.movieApiService = movieApiService;
    }

    public void fetchMovies() {
        movieApiService.getPopularMovies()
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe(movies -> {
                System.out.println("Movies size = " + movies.movies.size());
            }, error -> {
                System.out.println("Error = " + error.getMessage());
            });
    }
}
