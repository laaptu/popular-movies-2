package com.laaptu.popmovies.moviereviews.presentation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.laaptu.popmovies.R;
import com.laaptu.popmovies.common.AutoInjectActivity;
import com.laaptu.popmovies.models.Movie;
import com.laaptu.popmovies.models.Review;
import com.laaptu.popmovies.moviedetail.presentation.MovieDetailActivity;
import com.laaptu.popmovies.moviereviews.domain.MovieReviewViewModel;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

import android.widget.TextView;
import android.widget.Toast;

import org.parceler.Parcels;

import java.util.List;

import static com.laaptu.popmovies.common.ScreenConstants.EXTRA_MOVIE;

public class MovieReviewActivity extends AutoInjectActivity {

    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.rv_reviews)
    RecyclerView reviewsListView;

    @Override
    public int getLayoutId() {
        return R.layout.activity_movie_reviews;
    }

    private Movie movie;

    private MovieReviewViewModel movieReviewViewModel;

    public static Intent getLaunchingIntent(Context context, Movie movie) {
        Intent intent = new Intent(context, MovieReviewActivity.class);
        intent.putExtra(EXTRA_MOVIE, Parcels.wrap(movie));
        return intent;
    }

    private static Movie getMovieFromIntent(Intent intent) {
        if (intent == null || !intent.hasExtra(EXTRA_MOVIE))
            throw new RuntimeException("This Activity requires Movie");
        return Parcels.unwrap(intent.getParcelableExtra(EXTRA_MOVIE));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        movieReviewViewModel = getViewModel(MovieReviewViewModel.class);
        movie = getMovieFromIntent(getIntent());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        txtTitle.setText(movie.title);

        movieReviewViewModel.getReviewPublishSubject().observe(this, this::populateTrailers);
        movieReviewViewModel.fetchMovieReviews(String.valueOf(movie.movieId));
    }

    private void populateTrailers(List<Review> reviews) {
        if (reviews.size() == 0) {
            Toast.makeText(this, "No reviews yet", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        reviewsListView.addItemDecoration(new DividerItemDecoration(this,
            DividerItemDecoration.VERTICAL));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        reviewsListView.setLayoutManager(linearLayoutManager);
        ReviewListAdapter reviewListAdapter = new ReviewListAdapter(reviews);
        reviewsListView.setAdapter(reviewListAdapter);
    }

}
