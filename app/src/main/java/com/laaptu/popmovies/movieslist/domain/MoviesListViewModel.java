package com.laaptu.popmovies.movieslist.domain;

import com.laaptu.popmovies.R;
import com.laaptu.popmovies.models.Movie;
import com.laaptu.popmovies.models.MoviesList;
import com.laaptu.popmovies.movieslist.domain.MovieListUIModel.ListType;
import com.laaptu.popmovies.movieslist.domain.MovieListUIModel.ViewState;
import com.laaptu.popmovies.network.MovieApiService;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;

public class MoviesListViewModel {

    private MovieApiService movieApiService;

    private BehaviorSubject<Boolean> progressUISubject;
    private BehaviorSubject<Boolean> errorUISubject;
    private PublishSubject<Integer> snackBarTextSubject;

    private PublishSubject<List<Movie>> moviesListPublishSubject;

    private MovieListUIModel movieListUIModel = new MovieListUIModel(ViewState.Empty, ListType.Popular);

    @Inject
    public MoviesListViewModel(MovieApiService movieApiService) {
        this.movieApiService = movieApiService;

        progressUISubject = BehaviorSubject.createDefault(false);
        errorUISubject = BehaviorSubject.createDefault(false);

        moviesListPublishSubject = PublishSubject.create();
        snackBarTextSubject = PublishSubject.create();
    }

    public void fetchMovies(final ListType listType) {
        if (movieListUIModel.isViewStateReadyForLoading(listType)) {
            movieListUIModel.setLoadingState();
            showProgress(true);
            showErrorUI(false);

            Single<MoviesList> remoteMoviesList = listType == ListType.Popular ? movieApiService.getPopularMovies() :
                movieApiService.getTopRatedMovies();
            remoteMoviesList.subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    movies -> {
                        movieListUIModel.setLoadedStateWithListType(listType);
                        showProgress(false);
                        moviesListPublishSubject.onNext(movies.movies);
                    }
                    , error -> {
                        showProgress(false);
                        if (movieListUIModel.setErrorState())
                            showErrorUI(true);
                        else {
                            movieListUIModel.viewState = ViewState.Loaded;
                            snackBarTextSubject.onNext(R.string.error_fetch_movies);
                        }
                    });
        }
    }

    public void retryFetchMovies() {
        fetchMovies(movieListUIModel.listType);
    }


    private void showProgress(boolean show) {
        progressUISubject.onNext(show);
    }

    private void showErrorUI(boolean show) {
        errorUISubject.onNext(show);
    }


    public Observable<Boolean> getProgressUISubject() {
        return progressUISubject;
    }

    public Observable<Boolean> getErrorUISubject() {
        return errorUISubject;
    }

    public Observable<Integer> getSnackBarTextSubject() {
        return snackBarTextSubject;
    }

    public Observable<List<Movie>> getMoviesListSubject() {
        return moviesListPublishSubject;
    }
}
