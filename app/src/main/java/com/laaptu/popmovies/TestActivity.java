package com.laaptu.popmovies;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.laaptu.popmovies.common.AutoInjectActivity;
import com.laaptu.popmovies.di.viewmodel.ViewModelCreator;
import com.laaptu.popmovies.movieslist.domain.MoviesListViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import javax.inject.Inject;

public class TestActivity extends AutoInjectActivity {

    @Override
    public int getLayoutId() {
        return R.layout.activity_test;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        MoviesListViewModel moviesListViewModel = getViewModel(MoviesListViewModel.class);
        System.out.println(moviesListViewModel);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            }
        });
    }

}
