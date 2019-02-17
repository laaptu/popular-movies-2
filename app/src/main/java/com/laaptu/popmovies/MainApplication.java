package com.laaptu.popmovies;

import android.app.Activity;
import android.app.Application;

import com.laaptu.popmovies.di.DaggerAppComponent;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

public class MainApplication extends Application implements HasActivityInjector {

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        DaggerAppComponent.builder().build().inject(this);
        if(dispatchingAndroidInjector ==null)
            System.out.println("ANdroid Injector null");
        else
            System.out.println("ANdroid injector not null");
    }
}
