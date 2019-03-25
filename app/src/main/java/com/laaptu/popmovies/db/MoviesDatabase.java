package com.laaptu.popmovies.db;

import com.laaptu.popmovies.models.Movie;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Movie.class}, version = 1)
public abstract class MoviesDatabase extends RoomDatabase {
    public abstract MovieDao getMovieDao();

    public static String DB_NAME = "FavoritesMovies.db";
}
