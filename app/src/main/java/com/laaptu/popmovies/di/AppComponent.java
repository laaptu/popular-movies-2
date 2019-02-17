package com.laaptu.popmovies.di;

import com.laaptu.popmovies.MainApplication;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;

@Component(modules = {
    AndroidSupportInjectionModule.class,
    NetworkModule.class,
    ActivityModule.class
})
@Singleton
public interface AppComponent {
    void inject(MainApplication mainApplication);
}
