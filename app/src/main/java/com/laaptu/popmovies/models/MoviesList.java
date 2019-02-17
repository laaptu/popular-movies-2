package com.laaptu.popmovies.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MoviesList {
    @SerializedName("results")
    public List<Movie> movies;
}
