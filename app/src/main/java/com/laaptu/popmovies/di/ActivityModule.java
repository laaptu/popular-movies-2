package com.laaptu.popmovies.di;

import com.laaptu.popmovies.movieslist.MoviesListActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityModule {
    @ContributesAndroidInjector
    abstract MoviesListActivity bindMoviesListActivity();
}
