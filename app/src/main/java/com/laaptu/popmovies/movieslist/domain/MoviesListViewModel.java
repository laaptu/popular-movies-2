package com.laaptu.popmovies.movieslist.domain;

import com.laaptu.popmovies.R;
import com.laaptu.popmovies.common.LiveDataMutable;
import com.laaptu.popmovies.models.Movie;
import com.laaptu.popmovies.movieslist.domain.MovieListUIModel.ListType;
import com.laaptu.popmovies.movieslist.domain.MovieListUIModel.ViewState;
import com.laaptu.popmovies.movieslist.domain.interactors.FavoriteMovieInteraction;
import com.laaptu.popmovies.movieslist.domain.interactors.MovieApiServiceInteraction;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;

public class MoviesListViewModel extends ViewModel {

    private MovieApiServiceInteraction movieApiService;
    private FavoriteMovieInteraction favoriteMovieInteraction;

    private LiveDataMutable<Boolean> progressUISubject = new LiveDataMutable<>(false);
    private LiveDataMutable<Boolean> errorUISubject = new LiveDataMutable<>(false);
    private PublishSubject<Integer> snackBarTextSubject;

    private LiveDataMutable<List<Movie>> moviesListPublishSubject = new LiveDataMutable<>();
    private LiveDataMutable<MovieListUIModel> movieListUIModelLiveDataMutable = new LiveDataMutable<>(
        new MovieListUIModel(ViewState.Empty, ListType.Popular)
    );

    private MovieListUIModel movieListUIModel = movieListUIModelLiveDataMutable.getValue();

    @Inject
    public MoviesListViewModel(MovieApiServiceInteraction movieApiService, FavoriteMovieInteraction favoriteMovieInteraction) {
        this.movieApiService = movieApiService;
        this.favoriteMovieInteraction = favoriteMovieInteraction;
        snackBarTextSubject = PublishSubject.create();

    }

    public void init() {
        fetchMovies(movieListUIModel.listType);
    }

    public void fetchMovies(final ListType listType) {
        if (movieListUIModel.isViewStateReadyForLoading(listType)) {
            getMoviesByType(listType);
        }
    }

    public void refetchMovies() {
        if (movieListUIModel.listType == ListType.Favorites) {
            getMoviesByType(movieListUIModel.listType);
        }
    }

    private void getMoviesByType(ListType listType) {
        movieListUIModel.setLoadingState();
        showProgress(true);
        showErrorUI(false);

        getMovieFetchService(listType).subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                movies -> {
                    movieListUIModel.setLoadedStateWithListType(listType);
                    showProgress(false);
                    moviesListPublishSubject.postValue(movies);
                    if (movies.size() == 0)
                        snackBarTextSubject.onNext(R.string.empty_movies);
                }
                , error -> {
                    showProgress(false);
                    if (movieListUIModel.setErrorState()) {
                        showErrorUI(true);
                    } else {
                        movieListUIModel.viewState = ViewState.Loaded;
                        snackBarTextSubject.onNext(R.string.error_fetch_movies);
                    }
                });
    }


    private Single<List<Movie>> getMovieFetchService(ListType listType) {
        switch (listType) {
            case Favorites:
                return favoriteMovieInteraction.execute();
            case TopRated:
                return movieApiService.getTopRatedMovies();
            default:
                return movieApiService.getPopularMovies();
        }
    }

    public void retryFetchMovies() {
        fetchMovies(movieListUIModel.listType);
    }

    private void showProgress(boolean show) {
        progressUISubject.postValue(show);
    }

    private void showErrorUI(boolean show) {
        errorUISubject.postValue(show);
    }

    public LiveDataMutable<Boolean> getProgressUISubject() {
        return progressUISubject;
    }

    public LiveDataMutable<Boolean> getErrorUISubject() {
        return errorUISubject;
    }

    public Observable<Integer> getSnackBarTextSubject() {
        return snackBarTextSubject;
    }

    public LiveDataMutable<List<Movie>> getMoviesListSubject() {
        return moviesListPublishSubject;
    }
}
