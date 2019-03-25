package com.laaptu.popmovies.db;

import com.laaptu.popmovies.models.Movie;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import io.reactivex.Single;

import static androidx.room.OnConflictStrategy.REPLACE;


@Dao
public interface MovieDao {
    @Insert(onConflict = REPLACE)
    Single<Long> insertMovie(Movie movie);

    @Delete
    Single<Integer> deleteMovie(Movie movie);

    @Query("SELECT * FROM Movie WHERE movieId = :movieId")
    Single<Movie> getMovieById(Long movieId);

    @Query("SELECT * FROM Movie")
    Single<List<Movie>> getAllMovies();
}
