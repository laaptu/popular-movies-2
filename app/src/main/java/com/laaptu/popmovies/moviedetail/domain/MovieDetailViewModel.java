package com.laaptu.popmovies.moviedetail.domain;

import com.laaptu.popmovies.common.LiveDataMutable;
import com.laaptu.popmovies.models.Movie;
import com.laaptu.popmovies.models.Trailer;
import com.laaptu.popmovies.moviedetail.domain.interactors.FavoriteInteraction;
import com.laaptu.popmovies.moviedetail.domain.interactors.TrailerFetchInteraction;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.ViewModel;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class MovieDetailViewModel extends ViewModel {

    private FavoriteInteraction favoriteInteraction;
    private TrailerFetchInteraction trailerFetchInteraction;

    private LiveDataMutable<Boolean> isMovieFavoritePublishSubject = new LiveDataMutable<>();
    private LiveDataMutable<List<Trailer>> movieTrailersPublishSubject = new LiveDataMutable<>(new ArrayList<>());

    @Inject
    public MovieDetailViewModel(FavoriteInteraction favoriteInteraction, TrailerFetchInteraction trailerFetchInteraction) {
        this.favoriteInteraction = favoriteInteraction;
        this.trailerFetchInteraction = trailerFetchInteraction;
    }

    public void init(Movie movie) {
        if (movieTrailersPublishSubject.getValue().size() == 0) {
            trailerFetchInteraction.getMovieTrailers(String.valueOf(movie.movieId))
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    trailers -> {
                        if (trailers.size() > 0)
                            movieTrailersPublishSubject.postValue(trailers);
                    },
                    error ->
                        Timber.e("Error fetching trailers due to %s", error.getMessage())
                );
        }
        favoriteInteraction.isMovieFavorite(movie.movieId)
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(isFavorite -> isMovieFavoritePublishSubject.postValue(isFavorite));
    }

    public void toggleFavorite(Movie movie) {
        if (isMovieFavoritePublishSubject.getValue() == null)
            return;

        Boolean isMovieFavorite = isMovieFavoritePublishSubject.getValue();
        Single<Boolean> movieFavoriteInteraction = isMovieFavorite ? favoriteInteraction.removeFromFavorite(movie) :
            favoriteInteraction.makeMovieFavorite(movie);
        movieFavoriteInteraction
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(success ->
                    isMovieFavoritePublishSubject.postValue(!isMovieFavoritePublishSubject.getValue())
                ,
                error -> Timber.e("Error toggling favorite movie due to %s", error.getMessage())
            );
    }

    public LiveDataMutable<Boolean> getFavoritePublishSubject() {
        return isMovieFavoritePublishSubject;
    }

    public LiveDataMutable<List<Trailer>> getMovieTrailersPublishSubject() {
        return movieTrailersPublishSubject;
    }
}
