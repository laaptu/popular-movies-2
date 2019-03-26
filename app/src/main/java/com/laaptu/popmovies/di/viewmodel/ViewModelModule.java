package com.laaptu.popmovies.di.viewmodel;

import com.laaptu.popmovies.movieslist.domain.MoviesListViewModel;

import androidx.lifecycle.ViewModel;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(key = MoviesListViewModel.class)
    abstract ViewModel bindMoviesListViewModel(MoviesListViewModel moviesListViewModel);
}
