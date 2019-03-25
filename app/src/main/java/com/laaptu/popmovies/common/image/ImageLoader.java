package com.laaptu.popmovies.common.image;

import android.webkit.URLUtil;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public enum ImageLoader {
    Instance;

    public void loadImage(ImageLoadOptions imageLoadOptions, ImageView imageView) {
        if (URLUtil.isValidUrl(imageLoadOptions.url))
            Picasso.get()
                .load(imageLoadOptions.url)
                .placeholder(imageLoadOptions.placeHolderDrawableId)
                .error(imageLoadOptions.errorDrawableId)
                .into(imageView);
        else
            Picasso.get().load(imageLoadOptions.errorDrawableId).into(imageView);
    }
}
