package com.laaptu.popmovies.models;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;
import org.parceler.Parcel.Serialization;

@Parcel(Serialization.FIELD)
public class Review {
    @SerializedName("author")
    public String author;
    @SerializedName("content")
    public String content;
    @SerializedName("id")
    public String id;
}
