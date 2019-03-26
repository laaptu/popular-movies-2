package com.laaptu.popmovies.moviereviews.domain;

import com.laaptu.popmovies.common.LiveDataMutable;
import com.laaptu.popmovies.models.Review;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.ViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class MovieReviewViewModel extends ViewModel {

    private ReviewFetchInteraction reviewFetchInteraction;

    private LiveDataMutable<List<Review>> reviewPublishSubject = new LiveDataMutable<>();

    @Inject
    public MovieReviewViewModel(ReviewFetchInteraction reviewFetchInteraction) {
        this.reviewFetchInteraction = reviewFetchInteraction;
    }

    public void fetchMovieReviews(String movieId) {
        if (reviewPublishSubject.getValue() == null) {
            reviewFetchInteraction.fetchReviewsByMovieId(movieId)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(reviews -> reviewPublishSubject.postValue(reviews),
                    error -> Timber.e("Error fetching reviews due to %s", error.getMessage()));
        }
    }

    public LiveDataMutable<List<Review>> getReviewPublishSubject() {
        return reviewPublishSubject;
    }
}
