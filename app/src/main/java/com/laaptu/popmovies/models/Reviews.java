package com.laaptu.popmovies.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Reviews {
    @SerializedName("results")
    public List<Review> reviewList;
}
