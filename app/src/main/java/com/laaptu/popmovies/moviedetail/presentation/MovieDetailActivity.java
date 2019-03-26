package com.laaptu.popmovies.moviedetail.presentation;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff.Mode;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.laaptu.popmovies.R;
import com.laaptu.popmovies.common.AutoInjectActivity;
import com.laaptu.popmovies.common.image.ImageLoadOptions;
import com.laaptu.popmovies.common.image.ImageLoader;
import com.laaptu.popmovies.models.Movie;
import com.laaptu.popmovies.models.Trailer;
import com.laaptu.popmovies.moviedetail.domain.MovieDetailViewModel;
import com.laaptu.popmovies.moviedetail.presentation.TrailerListAdapter.TrailerItemViewHolder;
import com.laaptu.popmovies.moviereviews.presentation.MovieReviewActivity;
import com.laaptu.popmovies.movieslist.presentation.AspectImageView;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import org.parceler.Parcels;

import java.util.List;

import javax.inject.Inject;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

import static com.laaptu.popmovies.common.ScreenConstants.EXTRA_MOVIE;

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
    @BindView(R.id.img_favorite)
    ImageView imgFavorite;

    @BindView(R.id.txt_trailer_title)
    TextView txtTrailerTitle;
    @BindView(R.id.rv_trailers)
    RecyclerView trailersList;

    @Inject
    Bus bus;
    MovieDetailViewModel movieDetailViewModel;

    private Movie movie;

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
        movieDetailViewModel = getViewModel(MovieDetailViewModel.class);
        Movie movie = getMovieFromIntent(getIntent());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        populateViewWithMovie(movie);

        movieDetailViewModel.getFavoritePublishSubject().observe(this, this::setFavorite);
        movieDetailViewModel.getMovieTrailersPublishSubject().observe(this, this::populateTrailers);
        movieDetailViewModel.init(movie);
    }

    private void populateViewWithMovie(Movie movie) {
        this.movie = movie;
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

    private void setFavorite(Boolean isFavorite) {
        int colorId = isFavorite ? R.color.red : R.color.grey_disabled;
        int color = ContextCompat.getColor(this, colorId);
        imgFavorite.setColorFilter(color, Mode.SRC_IN);
    }

    private void watchYoutubeVideo(String id) {
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
            Uri.parse("http://www.youtube.com/watch?v=" + id));
        try {
            startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            startActivity(webIntent);
        }
    }

    @Subscribe
    public void onTrailerClicked(Trailer trailer) {
        watchYoutubeVideo(trailer.key);
    }

    @OnClick(R.id.img_favorite)
    public void onFavoriteClicked(View view) {
        movieDetailViewModel.toggleFavorite(movie);
    }

    @OnClick(R.id.btn_reviews)
    public void onReviewsClicked(View view) {
        startActivity(MovieReviewActivity.getLaunchingIntent(this, movie));
    }

    private void populateTrailers(List<Trailer> trailers) {
        if (trailers.size() == 0)
            return;
        txtTrailerTitle.setVisibility(View.VISIBLE);
        trailersList.addItemDecoration(new DividerItemDecoration(this,
            DividerItemDecoration.VERTICAL));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        trailersList.setLayoutManager(linearLayoutManager);
        TrailerListAdapter trailerListAdapter = new TrailerListAdapter(trailers, bus);
        trailersList.setAdapter(trailerListAdapter);
    }

    @Override
    protected void onPause() {
        bus.unregister(this);
        super.onPause();
    }

    @Override
    protected void onResume() {
        bus.register(this);
        super.onResume();
    }
}
