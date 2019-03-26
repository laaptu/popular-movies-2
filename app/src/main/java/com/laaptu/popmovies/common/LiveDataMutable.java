package com.laaptu.popmovies.common;

import androidx.lifecycle.LiveData;

public class LiveDataMutable<T> extends LiveData<T> {

    public LiveDataMutable() {
    }

    public LiveDataMutable(T value) {
        setValue(value);
    }

    @Override
    public void postValue(T value) {
        super.postValue(value);
    }

    @Override
    public void setValue(T value) {
        super.setValue(value);
    }
}
