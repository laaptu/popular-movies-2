package com.laaptu.popmovies.movieslist.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.laaptu.popmovies.R;
import com.laaptu.popmovies.common.AutoInjectActivity;
import com.laaptu.popmovies.common.ScreenConstants;
import com.laaptu.popmovies.models.Movie;
import com.laaptu.popmovies.moviedetail.presentation.MovieDetailActivity;
import com.laaptu.popmovies.movieslist.ItemClickInfo;
import com.laaptu.popmovies.movieslist.domain.MovieListUIModel.ListType;
import com.laaptu.popmovies.movieslist.domain.MoviesListViewModel;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.disposables.Disposable;

public class MoviesListActivity extends AutoInjectActivity {

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
        moviesListViewModel = getViewModel(MoviesListViewModel.class);
        swipeRefreshLayout.setEnabled(false);

        moviesListViewModel.getProgressUISubject().observe(this, refresh ->
            swipeRefreshLayout.setRefreshing(refresh)
        );
        moviesListViewModel.getErrorUISubject().observe(this, showError ->
            tvErrorView.setVisibility(showError ? View.VISIBLE : View.GONE));

        moviesListViewModel.getMoviesListSubject().observe(this, movies ->
            populateMovieListView(movies));

        moviesListViewModel.init();
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

        Disposable disposable = moviesListViewModel.getSnackBarTextSubject()
            .subscribe(
                stringResource -> showInfo(stringResource)
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
            case R.id.menu_item_favorites:
                fetchMovies(ListType.Favorites);
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
    public void onMovieSelected(ItemClickInfo<Movie, ImageView> itemClickInfo) {
        Intent movieDetailIntent = MovieDetailActivity.getLaunchingIntent(this, itemClickInfo.item);
        ActivityOptionsCompat options = ActivityOptionsCompat.
            makeSceneTransitionAnimation(this, itemClickInfo.view, getString(R.string.transition_thumb));
        startActivityForResult(movieDetailIntent, ScreenConstants.RETURN_FROM_MOVIE_DETAIL, options.toBundle());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ScreenConstants.RETURN_FROM_MOVIE_DETAIL)
            moviesListViewModel.refetchMovies();
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
