package com.laaptu.popmovies.di;

import com.laaptu.popmovies.moviereviews.presentation.MovieReviewActivity;
import com.laaptu.popmovies.moviedetail.presentation.MovieDetailActivity;
import com.laaptu.popmovies.movieslist.presentation.MoviesListActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityModule {
    @ContributesAndroidInjector
    abstract MoviesListActivity bindMoviesListActivity();

    @ContributesAndroidInjector
    abstract MovieDetailActivity bindMovieDetailActivity();

    @ContributesAndroidInjector
    abstract MovieReviewActivity bindTestActivity();
}
