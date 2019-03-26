package com.laaptu.popmovies.moviereviews.domain;

import com.laaptu.popmovies.models.Review;
import com.laaptu.popmovies.network.MovieApiService;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class ReviewFetchInteraction {

    private MovieApiService movieApiService;

    @Inject
    public ReviewFetchInteraction(MovieApiService movieApiService) {
        this.movieApiService = movieApiService;
    }

    public Single<List<Review>> fetchReviewsByMovieId(String movieId) {
        return movieApiService.getReviewsOfMovieWithId(movieId).map(reviews -> reviews.reviewList);
    }
}
