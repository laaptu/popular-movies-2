package com.laaptu.popmovies.common.image;

import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public enum ImageLoader {
    Instance;

    public void loadImage(ImageLoadOptions imageLoadOptions, ImageView imageView) {
        Picasso.get()
            .load(imageLoadOptions.url)
            .placeholder(imageLoadOptions.placeHolderDrawableId)
            .error(imageLoadOptions.errorDrawableId)
            .into(imageView);
    }
}
