package com.laaptu.popmovies;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.laaptu.popmovies.network.APIConstants;
import com.laaptu.popmovies.models.Movie;
import com.laaptu.popmovies.models.deserializers.MovieDeserializer;

import org.junit.Assert;
import org.junit.Test;

public class MovieDeserializationTest {

    @Test
    public void validateImagePathIsAppendedByMovieDeserializer() {
        String movieJsonString = TestUtils.getStringFromResources("movie.json");
        Gson gson = new GsonBuilder().registerTypeAdapter(Movie.class, new MovieDeserializer()).create();
        Movie movie = gson.fromJson(movieJsonString, Movie.class);
        Assert.assertTrue(movie.imagePath.contains(APIConstants.URL_BASE_IMAGE_PATH));
    }
}
