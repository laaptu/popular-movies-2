package com.laaptu.popmovies.common;

import android.os.Bundle;
import android.view.MenuItem;

import com.laaptu.popmovies.di.viewmodel.ViewModelCreator;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public abstract class AutoInjectActivity extends AppCompatActivity {

    public abstract int getLayoutId();

    private CompositeDisposable disposables = new CompositeDisposable();

    @Inject
    ViewModelCreator viewModelCreator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidInjection.inject(this);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
    }

    public <T> Observable<T> getMainThreadSubscription(Observable<T> observable) {
        return observable.subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread());
    }

    public void addDisposable(Disposable disposable) {
        disposables.add(disposable);
    }

    public void clearDisposable() {
        disposables.clear();
    }

    public <T extends ViewModel> T getViewModel(Class<T> clazz) {
        return ViewModelProviders.of(this, viewModelCreator).get(clazz);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}
