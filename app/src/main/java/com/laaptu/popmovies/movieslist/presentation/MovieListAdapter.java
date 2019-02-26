package com.laaptu.popmovies.movieslist.presentation;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.laaptu.popmovies.R;
import com.laaptu.popmovies.common.image.ImageLoadOptions;
import com.laaptu.popmovies.common.image.ImageLoader;
import com.laaptu.popmovies.models.Movie;
import com.laaptu.popmovies.movieslist.presentation.MovieListAdapter.MovieItemViewHolder;
import com.squareup.otto.Bus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MovieListAdapter extends RecyclerView.Adapter<MovieItemViewHolder> {

    private List<Movie> movieList;
    private ImageLoadOptions.Builder imageLoadOptionsBuilder = new ImageLoadOptions.Builder();
    private Bus bus;

    public MovieListAdapter(List<Movie> movieList, Bus bus) {
        this.movieList = movieList;
        this.bus = bus;
    }

    @NonNull
    @Override
    public MovieItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.movie_item,
            viewGroup, false);
        return new MovieItemViewHolder(view, bus);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieItemViewHolder movieItemViewHolder, int position) {
        movieItemViewHolder.updateView(movieList.get(position), imageLoadOptionsBuilder);
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public static class MovieItemViewHolder extends ViewHolder {

        @BindView(R.id.img_thumb)
        AspectImageView imgThumb;

        Movie movie;
        Bus bus;

        MovieItemViewHolder(@NonNull View itemView, Bus bus) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.bus = bus;
        }

        public void updateView(Movie movie, ImageLoadOptions.Builder builder) {
            this.movie = movie;
            ImageLoader.Instance.loadImage(builder.setImageUrl(movie.imagePath).build(), imgThumb);
        }

        @OnClick(R.id.img_thumb)
        public void onThumbClicked() {
            bus.post(movie);
        }
    }
}
