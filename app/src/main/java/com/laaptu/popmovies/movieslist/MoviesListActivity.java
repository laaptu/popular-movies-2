package com.laaptu.popmovies.movieslist;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.laaptu.popmovies.R;
import com.laaptu.popmovies.network.MovieApiService;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class MoviesListActivity extends AppCompatActivity {

    @Inject
    MovieApiService movieApiService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidInjection.inject(this);
        setContentView(R.layout.activity_main);
    }


}
