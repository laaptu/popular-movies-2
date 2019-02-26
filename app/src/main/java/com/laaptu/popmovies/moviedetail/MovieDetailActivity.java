package com.laaptu.popmovies.moviedetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.laaptu.popmovies.R;
import com.laaptu.popmovies.common.AutoInjectActivity;
import com.laaptu.popmovies.common.image.ImageLoadOptions;
import com.laaptu.popmovies.common.image.ImageLoader;
import com.laaptu.popmovies.models.Movie;
import com.laaptu.popmovies.movieslist.presentation.AspectImageView;

import org.parceler.Parcels;

import butterknife.BindView;

public class MovieDetailActivity extends AutoInjectActivity {


    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.img_thumb)
    AspectImageView imgThumb;
    @BindView(R.id.txt_year)
    TextView txtYear;
    @BindView(R.id.txt_rating)
    TextView txtRating;
    @BindView(R.id.txt_summary)
    TextView txtSummary;

    public static final String EXTRA_MOVIE = "Movie";

    public static Intent getLaunchingIntent(Context context, Movie movie) {
        Intent intent = new Intent(context, MovieDetailActivity.class);
        intent.putExtra(EXTRA_MOVIE, Parcels.wrap(movie));
        return intent;
    }

    private static Movie getMovieFromIntent(Intent intent) {
        if (intent == null || !intent.hasExtra(EXTRA_MOVIE))
            throw new RuntimeException("This Activity requires Movie");
        return Parcels.unwrap(intent.getParcelableExtra(EXTRA_MOVIE));
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_movie_detail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Movie movie = getMovieFromIntent(getIntent());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        populateViewWithMovie(movie);
    }

    private void populateViewWithMovie(Movie movie) {
        txtTitle.setText(movie.title);
        String releaseDate = movie.releaseDate;
        if (releaseDate.contains("-"))
            releaseDate = releaseDate.split("-")[0];
        txtYear.setText(releaseDate);
        txtRating.setText(String.valueOf(movie.votes));
        txtSummary.setText(movie.overview);
        ImageLoadOptions imageLoadOptions = new ImageLoadOptions.Builder().setImageUrl(movie.imagePath).build();
        ImageLoader.Instance.loadImage(imageLoadOptions, imgThumb);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}
