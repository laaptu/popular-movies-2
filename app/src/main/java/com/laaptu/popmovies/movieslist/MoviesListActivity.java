package com.laaptu.popmovies.movieslist;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.laaptu.popmovies.R;
import com.laaptu.popmovies.models.Movie;

import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class MoviesListActivity extends AppCompatActivity {

    @Inject
    MoviesListViewModel moviesListViewModel;

    RecyclerView rvMoviesList;
    SwipeRefreshLayout swipeRefreshLayout;
    TextView tvErrorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidInjection.inject(this);
        setContentView(R.layout.activity_main);
        rvMoviesList = findViewById(R.id.rv_movies);
        swipeRefreshLayout = findViewById(R.id.srl_refresh_view);
        swipeRefreshLayout.setEnabled(false);
        moviesListViewModel.fetchMovies();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_sort, menu);
        return true;
    }

    private void populateMovieListView(List<Movie> movieList) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, getColumnWidth());
        MovieListAdapter movieListAdapter = new MovieListAdapter(movieList);
        rvMoviesList.setLayoutManager(gridLayoutManager);
        rvMoviesList.setAdapter(movieListAdapter);
    }

    private void showProgress(boolean show) {
        swipeRefreshLayout.setRefreshing(show);
    }

    private void showErrorView(boolean show) {
        int visibility = show ? View.VISIBLE : View.GONE;
        tvErrorView.setVisibility(visibility);
    }

    private int getColumnWidth() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels > displayMetrics.heightPixels ?
            displayMetrics.widthPixels / 3 : displayMetrics.widthPixels / 2;
    }
}
