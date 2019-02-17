package com.laaptu.popmovies.models.deserializers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.laaptu.popmovies.network.APIConstants;
import com.laaptu.popmovies.models.Movie;

import java.lang.reflect.Type;

public class MovieDeserializer implements JsonDeserializer<Movie> {

    private Gson gson = new GsonBuilder().create();

    @Override
    public Movie deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Movie movie = gson.fromJson(json, Movie.class);
        movie.imagePath = APIConstants.URL_BASE_IMAGE_PATH.concat(movie.imagePath);
        return movie;
    }
}
