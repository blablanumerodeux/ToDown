package com.jerkcrew.todown;

import java.util.ArrayList;

import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;

import com.jerkcrew.todown.Entity.Movie;
import com.jerkcrew.todown.Modele.Allocine;

public class EndlessScrollListener implements OnScrollListener {

    private int visibleThreshold = 6;
    private int currentPage = 0;
    private int previousTotal = 0;
    private boolean loading = true;
    private Allocine allocine;
    private String query;
    private OnlineImageAdapter adapter;

    public EndlessScrollListener(int visibleThreshold, Allocine allocine, String query, OnlineImageAdapter adapter) {
    	this.visibleThreshold = visibleThreshold;
    	this.allocine= allocine;
    	this.query= query;
        this.adapter= adapter;
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
            int visibleItemCount, int totalItemCount) {
        if (loading) {
            if (totalItemCount > previousTotal) {
                loading = false;
                previousTotal = totalItemCount;
                currentPage++;
            }
        }
        if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
            // I load the next page of gigs using a background task,
            // but you can call any function here.
            ArrayList<Movie> movies = allocine.searchMovie(query, currentPage + 1);
            ArrayList<Movie> oldMovies = this.adapter.getMovieList();
            oldMovies.addAll(oldMovies.size(), movies);
            loading = true;
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
    }
}