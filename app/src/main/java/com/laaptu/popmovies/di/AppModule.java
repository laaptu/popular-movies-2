package com.laaptu.popmovies.di;

import android.content.Context;

import com.laaptu.popmovies.MainApplication;
import com.laaptu.popmovies.db.MoviesDatabase;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import androidx.room.Room;
import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    @Singleton
    @Provides
    public static Bus getBus() {
        return new Bus();
    }

//    @Provides
//    public static Context getAppContext() {
//        return MainApplication.getAppContext();
//    }
//
//    @Singleton
//    @Provides
//    public static MoviesDatabase getMoviesDatabase(Context context) {
//        return Room.databaseBuilder(context, MoviesDatabase.class,
//            MoviesDatabase.DB_NAME).build();
//    }

}
