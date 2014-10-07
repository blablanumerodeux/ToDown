package com.jerkcrew.todown;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.SearchManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.jerkcrew.todown.Entity.Movie;
import com.jerkcrew.todown.Modele.Allocine;
import com.jerkcrew.todown.Modele.CurlLike;
import com.jerkcrew.todown.Modele.ImageDownloadTask;
 
public class SearchResultsActivity extends Activity {
 
    private TextView txtQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_search_results);
        setContentView(R.layout.allocine_grid_view);
 
        // get the action bar
        ActionBar actionBar = getActionBar();
 
        // Enabling Back navigation on Action Bar icon
        actionBar.setDisplayHomeAsUpEnabled(true);
 
        handleIntent(getIntent());
    }
 
    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }
 
    /**
     * Handling intent data
     */
    private void handleIntent(Intent intent) {
    	
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            String partner = getResources().getString(R.string.ALLOCINE_PARTNER_KEY);
            String secretKey = getResources().getString(R.string.ALLOCINE_SECRET_KEY);
            
            Allocine allocine = new Allocine(partner, secretKey);
            
            ArrayList<Movie> movies = new ArrayList<Movie>();
            movies = allocine.searchMovie(query);
            
            //ListView rootView = (ListView) findViewById(R.id.allocine_grid_view);
            GridView rootView = (GridView) findViewById(R.id.allocine_grid_view);
            
//        GridView gridView = (GridView) rootView.findViewById(R.id.allocine_grid_view);
//        OnlineImageAdapter adapter = (OnlineImageAdapter) gridView.getAdapter();
//        adapter.setMovieList(movieList);
//        gridView.setAdapter(adapter);
            OnlineImageAdapter adapter = new OnlineImageAdapter(getBaseContext(), movies);
            rootView.setAdapter(adapter);
            rootView.setOnScrollListener(new EndlessScrollListener(9,allocine, query, adapter));
        }
 
    }
    
}