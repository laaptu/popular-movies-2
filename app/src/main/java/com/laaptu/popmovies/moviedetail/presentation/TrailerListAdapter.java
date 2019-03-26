package com.laaptu.popmovies.moviedetail.presentation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.laaptu.popmovies.R;
import com.laaptu.popmovies.models.Trailer;
import com.laaptu.popmovies.moviedetail.presentation.TrailerListAdapter.TrailerItemViewHolder;
import com.squareup.otto.Bus;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TrailerListAdapter extends RecyclerView.Adapter<TrailerItemViewHolder> {

    private List<Trailer> trailers;
    private Bus bus;

    public TrailerListAdapter(List<Trailer> trailers, Bus bus) {
        this.trailers = trailers;
        this.bus = bus;
    }

    @NonNull
    @Override
    public TrailerItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.trailer_item,
            viewGroup, false);
        return new TrailerItemViewHolder(view, bus);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerItemViewHolder holder, int position) {
        holder.updateView(trailers.get(position), position);
    }

    @Override
    public int getItemCount() {
        return trailers.size();
    }

    public static class TrailerItemViewHolder extends ViewHolder {

        @BindView(R.id.txt_trailer)
        TextView txtTrailer;

        Trailer trailer;
        Bus bus;

        public TrailerItemViewHolder(@NonNull View itemView, Bus bus) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.bus = bus;
        }

        public void updateView(Trailer trailer, int position) {
            this.trailer = trailer;
            String title = txtTrailer.getContext().getString(R.string.trailer, position);
            txtTrailer.setText(title);
        }

        @OnClick(R.id.trailer_container)
        public void onTrailerClicked() {
            bus.post(trailer);
        }
    }
}
