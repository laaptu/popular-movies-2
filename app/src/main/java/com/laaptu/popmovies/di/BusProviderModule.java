package com.laaptu.popmovies.di;

import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class BusProviderModule {

    @Singleton
    @Provides
    public Bus getBus() {
        return new Bus();
    }
}
