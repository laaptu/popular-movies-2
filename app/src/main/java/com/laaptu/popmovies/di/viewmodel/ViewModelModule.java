package com.laaptu.popmovies.di.viewmodel;

import com.laaptu.popmovies.moviedetail.domain.MovieDetailViewModel;
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

    @Binds
    @IntoMap
    @ViewModelKey(key = MovieDetailViewModel.class)
    abstract ViewModel bindMovieDetailViewModle(MovieDetailViewModel movieDetailViewModel);
}
