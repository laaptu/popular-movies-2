package com.laaptu.popmovies.di;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.laaptu.popmovies.models.Movie;
import com.laaptu.popmovies.models.deserializers.MovieDeserializer;
import com.laaptu.popmovies.network.APIConstants;
import com.laaptu.popmovies.network.MovieApiService;
import com.laaptu.popmovies.network.RequestInterceptor;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {

    @Provides
    static Retrofit getRetrofit(Gson gson, OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
            .baseUrl(APIConstants.URL_BASE)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build();
    }

    @Provides
    static Gson getGson() {
        return new GsonBuilder()
            .registerTypeAdapter(Movie.class, new MovieDeserializer())
            .create();
    }

    @Provides
    static OkHttpClient getOkHttpClient() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(Level.BODY);

        return new OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addNetworkInterceptor(new RequestInterceptor())
            .connectTimeout(APIConstants.TIMEOUT_CONNECTION_IN_SECS, TimeUnit.SECONDS)
            .readTimeout(APIConstants.TIMEOUT_READ_IN_SECS, TimeUnit.SECONDS)
            .build();
    }

    @Provides
    @Singleton
    static MovieApiService getMovieService(Retrofit retrofit) {
        return retrofit.create(MovieApiService.class);
    }
}
