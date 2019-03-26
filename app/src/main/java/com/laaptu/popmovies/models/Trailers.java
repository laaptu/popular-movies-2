package com.laaptu.popmovies.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Trailers {
    @SerializedName("results")
    public List<Trailer> trailerList;
}
