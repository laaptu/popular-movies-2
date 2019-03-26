package com.laaptu.popmovies.moviereviews.presentation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.laaptu.popmovies.R;
import com.laaptu.popmovies.models.Review;
import com.laaptu.popmovies.moviereviews.presentation.ReviewListAdapter.ReviewItemViewHolder;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ReviewListAdapter extends RecyclerView.Adapter<ReviewItemViewHolder> {

    private List<Review> reviews;

    public ReviewListAdapter(List<Review> reviews) {
        this.reviews = reviews;
    }

    @NonNull
    @Override
    public ReviewItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.review_item,
            viewGroup, false);
        return new ReviewItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewItemViewHolder holder, int position) {
        holder.updateView(reviews.get(position));
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    public static class ReviewItemViewHolder extends ViewHolder {

        @BindView(R.id.txt_reviewer)
        TextView txtReviewer;

        @BindView(R.id.txt_review)
        TextView txtReview;

        Review review;

        public ReviewItemViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void updateView(Review review) {
            txtReviewer.setText(review.author);
            txtReview.setText(review.content);
        }

    }
}
