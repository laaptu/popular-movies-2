package com.laaptu.popmovies.movieslist.presentation;

import android.content.Intent;
import android.os.Bundle;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.laaptu.popmovies.R;
import com.laaptu.popmovies.common.AutoInjectActivity;
import com.laaptu.popmovies.models.Movie;
import com.laaptu.popmovies.moviedetail.MovieDetailActivity;
import com.laaptu.popmovies.movieslist.domain.MovieListUIModel.ListType;
import com.laaptu.popmovies.movieslist.domain.MoviesListViewModel;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.disposables.Disposable;

public class MoviesListActivity extends AutoInjectActivity {

    @Inject
    MoviesListViewModel moviesListViewModel;

    @Inject
    Bus bus;

    @BindView(R.id.rv_movies)
    RecyclerView rvMoviesList;
    @BindView(R.id.srl_refresh_view)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.txt_error)
    TextView tvErrorView;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        swipeRefreshLayout.setEnabled(false);
        fetchMovies(ListType.Popular);
    }

    @Override
    protected void onPause() {
        super.onPause();
        clearDisposable();
        bus.unregister(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Disposable disposable = getMainThreadSubscription(moviesListViewModel.getErrorUISubject()).subscribe(
            showError ->
                tvErrorView.setVisibility(showError ? View.VISIBLE : View.GONE)
        );
        addDisposable(disposable);

        disposable = getMainThreadSubscription(moviesListViewModel.getProgressUISubject()).subscribe(
            showProgress -> swipeRefreshLayout.setRefreshing(showProgress)
        );
        addDisposable(disposable);

        disposable = moviesListViewModel.getSnackBarTextSubject()
            .subscribe(
                stringResource -> showInfo(stringResource)
            );

        addDisposable(disposable);

        disposable = moviesListViewModel.getMoviesListSubject().subscribe(
            movies -> populateMovieListView(movies)
        );

        addDisposable(disposable);

        bus.register(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_sort, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_popular:
                fetchMovies(ListType.Popular);
                break;
            case R.id.menu_item_top_rated:
                fetchMovies(ListType.TopRated);
                break;

        }
        return true;
    }

    @OnClick(R.id.txt_error)
    public void onTapToRetryClicked() {
        moviesListViewModel.retryFetchMovies();
    }

    private void fetchMovies(ListType listType) {
        moviesListViewModel.fetchMovies(listType);
    }

    @Subscribe
    public void onMovieSelected(Movie movie) {
        Intent movieDetailIntent = MovieDetailActivity.getLaunchingIntent(this, movie);
        startActivity(movieDetailIntent);
    }


    private void populateMovieListView(List<Movie> movieList) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, getColumnWidth());
        gridLayoutManager.setSpanCount(2);
        MovieListAdapter movieListAdapter = new MovieListAdapter(movieList, bus);
        rvMoviesList.setLayoutManager(gridLayoutManager);
        rvMoviesList.setAdapter(movieListAdapter);
        rvMoviesList.setVisibility(View.VISIBLE);
    }

    private int getColumnWidth() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels > displayMetrics.heightPixels ?
            displayMetrics.widthPixels / 3 : displayMetrics.widthPixels / 2;
    }

    private void showInfo(Integer stringResource) {
        Snackbar.make(rvMoviesList, getString(stringResource), Snackbar.LENGTH_SHORT).show();
    }
}
