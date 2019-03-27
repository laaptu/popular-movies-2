package com.laaptu.popmovies.movieslist;

import android.view.View;

public class ItemClickInfo<T, V extends View> {

    public T item;
    public V view;

    public ItemClickInfo(T item, V view) {
        this.item = item;
        this.view = view;
    }
}
