package com.laaptu.popmovies.network;

import com.laaptu.popmovies.models.MoviesList;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface MovieApiService {
    @GET(APIConstants.PATH_POPULAR_MOVIES)
    Single<MoviesList> getPopularMovies();
}
