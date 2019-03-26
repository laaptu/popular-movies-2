package com.laaptu.popmovies.network;

import com.laaptu.popmovies.models.MoviesList;
import com.laaptu.popmovies.models.Reviews;
import com.laaptu.popmovies.models.Trailers;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MovieApiService {
    @GET(APIConstants.PATH_POPULAR_MOVIES)
    Single<MoviesList> getPopularMovies();

    @GET(APIConstants.PATH_TOP_RATED_MOVIES)
    Single<MoviesList> getTopRatedMovies();

    @GET(APIConstants.PATH_MOVIE_REVIEW)
    Single<Reviews> getReviewsOfMovieWithId(@Path(APIConstants.PATH_PARAM_MOVIE_ID) String movieId);

    @GET(APIConstants.PATH_MOVIE_TRAILER)
    Single<Trailers> getTrailersOfMovieWithId(@Path(APIConstants.PATH_PARAM_MOVIE_ID) String movieId);
}
