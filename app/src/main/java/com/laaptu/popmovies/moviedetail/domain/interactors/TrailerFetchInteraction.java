package com.laaptu.popmovies.moviedetail.domain.interactors;

import com.laaptu.popmovies.models.Trailer;
import com.laaptu.popmovies.network.MovieApiService;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import timber.log.Timber;

public class TrailerFetchInteraction {

    private MovieApiService movieApiService;

    @Inject
    public TrailerFetchInteraction(MovieApiService movieApiService) {
        this.movieApiService = movieApiService;
    }

    public Single<List<Trailer>> getMovieTrailers(String movieId) {
        return movieApiService.getTrailersOfMovieWithId(movieId).map(trailers -> trailers.trailerList)
            .onErrorReturn(throwable -> {
                Timber.e("Error fetching moving trailers due to %s", throwable.getMessage());
                return new ArrayList<>();
            });
    }
}
