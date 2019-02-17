package com.laaptu.popmovies.models;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;
import org.parceler.Parcel.Serialization;

@Parcel(Serialization.FIELD)
public class Movie {
    @SerializedName("id")
    public long movieId;
    @SerializedName("video")
    public boolean isVideo = false;
    @SerializedName("title")
    public String title = "";
    @SerializedName("poster_path")
    public String imagePath = "";
    @SerializedName("popularity")
    float popularity;
    @SerializedName("vote_average")
    float votes;
    @SerializedName("overview")
    String overview = "";
    @SerializedName("release_date")
    String releaseDate = "";
}
