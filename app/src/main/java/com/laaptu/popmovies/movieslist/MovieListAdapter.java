package com.laaptu.popmovies.movieslist;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.laaptu.popmovies.R;
import com.laaptu.popmovies.models.Movie;
import com.laaptu.popmovies.movieslist.MovieListAdapter.MovieItemViewHolder;

import java.util.List;

public class MovieListAdapter extends RecyclerView.Adapter<MovieItemViewHolder> {

    private List<Movie> movieList;

    public MovieListAdapter(List<Movie> movieList) {
        this.movieList = movieList;
    }

    public void setNewMovieList(List<Movie> movieList) {
        if (movieList == null)
            return;
        this.movieList = movieList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.movie_item,
            viewGroup, false);
        return new MovieItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieItemViewHolder movieItemViewHolder, int position) {

    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    static class MovieItemViewHolder extends ViewHolder {

        MovieItemViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
