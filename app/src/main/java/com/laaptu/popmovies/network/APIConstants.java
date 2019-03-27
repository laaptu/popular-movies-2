package com.laaptu.popmovies.network;

public class APIConstants {
    public static final String QUERY_API_KEY = "api_key";
    public static final String URL_BASE = "https://api.themoviedb.org/3/";
    public static final String URL_BASE_IMAGE_PATH = "https://image.tmdb.org/t/p/w342";
    public static final String PATH_POPULAR_MOVIES = "movie/popular";
    public static final String PATH_TOP_RATED_MOVIES = "movie/top_rated";
    public static final String PATH_PARAM_MOVIE_ID = "movie_id";
    public static final String PATH_MOVIE_REVIEW = "movie/{" + PATH_PARAM_MOVIE_ID + "}/reviews";
    public static final String PATH_MOVIE_TRAILER = "movie/{" + PATH_PARAM_MOVIE_ID + "}/videos";

    public static final int TIMEOUT_CONNECTION_IN_SECS = 40;
    public static final int TIMEOUT_READ_IN_SECS = 40;
}
