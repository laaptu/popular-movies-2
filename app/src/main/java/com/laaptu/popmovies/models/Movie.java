package com.laaptu.popmovies.models;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;
import org.parceler.Parcel.Serialization;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Parcel(Serialization.FIELD)
@Entity
public class Movie {
    @SerializedName("id")
    @PrimaryKey
    public long movieId;
    @SerializedName("title")
    public String title = "";
    @SerializedName("poster_path")
    public String imagePath = "";
    @SerializedName("popularity")
    public float popularity;
    @SerializedName("vote_average")
    public float votes;
    @SerializedName("overview")
    public String overview = "";
    @SerializedName("release_date")
    public String releaseDate = "";
}
