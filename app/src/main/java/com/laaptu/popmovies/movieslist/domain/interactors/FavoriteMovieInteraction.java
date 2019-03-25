package com.laaptu.popmovies.movieslist.domain.interactors;

import com.laaptu.popmovies.db.MovieDao;
import com.laaptu.popmovies.models.Movie;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class FavoriteMovieInteraction {
    private MovieDao movieDao;

    @Inject
    public FavoriteMovieInteraction(MovieDao movieDao) {
        this.movieDao = movieDao;
    }

    public Single<List<Movie>> execute() {
        return movieDao.getAllMovies();
    }
}
