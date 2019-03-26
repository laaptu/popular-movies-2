package com.laaptu.popmovies.moviedetail.domain.interactors;

import com.laaptu.popmovies.db.MovieDao;
import com.laaptu.popmovies.models.Movie;

import javax.inject.Inject;

import io.reactivex.Single;
import timber.log.Timber;

public class FavoriteInteraction {
    private MovieDao movieDao;

    @Inject
    public FavoriteInteraction(MovieDao movieDao) {
        this.movieDao = movieDao;
    }

    public Single<Boolean> makeMovieFavorite(Movie movie) {
        return movieDao.insertMovie(movie)
            .map(insertedId ->
            {
                Timber.d("Movie successfully marked as favorite %d", insertedId);
                return true;
            })
            .onErrorReturn(throwable ->
            {
                Timber.e("Error making movie favorite due to %s", throwable.getMessage());
                return false;
            });
    }

    public Single<Boolean> removeFromFavorite(Movie movie) {
        return movieDao.deleteMovie(movie).map(deletionId -> {
            Timber.d("Movie removed from favorite %d", deletionId);
            return true;
        })
            .onErrorReturn(throwable -> {
                Timber.e("Error removing movie from favorite due to %s", throwable.getMessage());
                return false;
            });
    }

    public Single<Boolean> isMovieFavorite(Long movieId) {
        return movieDao.getMovieById(movieId)
            .map(movie -> movie.movieId == movieId)
            .onErrorReturn(throwable -> false);
    }
}
