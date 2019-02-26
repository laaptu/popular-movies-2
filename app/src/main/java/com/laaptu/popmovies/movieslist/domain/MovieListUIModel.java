package com.laaptu.popmovies.movieslist.domain;

import com.laaptu.popmovies.models.Movie;

import java.util.List;

public class MovieListUIModel {
    public enum ViewState {
        Empty,
        Error,
        EmptyProgress,
        Loaded,
        LoadedProgress
    }

    public enum ListType {
        Popular,
        TopRated
    }

    public ViewState viewState;
    public ListType listType;


    public MovieListUIModel(ViewState viewState, ListType listType) {
        this.viewState = viewState;
        this.listType = listType;
    }

    public boolean isViewStateReadyForLoading(ListType listType) {
        if (viewState == ViewState.Loaded && this.listType == listType)
            return false;
        if (viewState == ViewState.EmptyProgress || viewState == ViewState.LoadedProgress)
            return false;
        return true;
    }

    public void setLoadingState() {
        if (viewState == ViewState.Empty || viewState == ViewState.Error)
            viewState = ViewState.EmptyProgress;
        if (viewState == ViewState.Loaded)
            viewState = ViewState.LoadedProgress;
    }

    public boolean setErrorState() {
        if (viewState == ViewState.Empty || viewState == ViewState.EmptyProgress) {
            viewState = ViewState.Error;
            return true;
        }
        return false;
    }

    public void setLoadedStateWithListType(ListType listType) {
        this.listType = listType;
        viewState = ViewState.Loaded;
    }
}
