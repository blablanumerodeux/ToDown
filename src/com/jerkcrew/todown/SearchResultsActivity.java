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
 
//        txtQuery = (TextView) findViewById(R.id.txtQuery);
        
        
        
        
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
 
            /**
             * Use this query to display search results like
             * 1. Getting the data from SQLite and showing in listview
             * 2. Making webrequest and displaying the data
             * For now we just display the query only
             */
            LinkedHashMap<String, String> data = new LinkedHashMap<String, String>();
     	    data.put("partner", ""+getResources().getString(R.string.ALLOCINE_PARTNER_KEY));
     	    data.put("q", query);
     	    data.put("format", "json");
     	    data.put("filter", "movie");
     	    data.put("count", "9");
     	    
     	    String url = Allocine.buildURL(getResources().getString(R.string.ALLOCINE_SECRET_KEY),"search",data);

     	    CurlLike curl = new CurlLike();
     	    String allocineAnswer;
    		try {
    			allocineAnswer = curl.execute(url).get();
    			
    		    JSONObject jsonObj = new JSONObject(allocineAnswer);
    		    
    		    JSONObject feed = jsonObj.getJSONObject("feed");
    		    JSONArray movie = feed.getJSONArray("movie");
    		    JSONArray results = feed.getJSONArray("results");
    		    int page = feed.getInt("page"); 
    		    int count = feed.getInt("count"); 
    		    JSONArray infoResults= feed.getJSONArray("results");
    		    String typeOfItemsFound = infoResults.getJSONObject(0).getString("type");
    		    int nbItemsFound = infoResults.getJSONObject(0).getInt("$");
    		    ArrayList<Movie> movies = new ArrayList<Movie>();
    		    for (int i =0; i<movie.length();i++){
    		    	movies.add(new Movie(movie.getJSONObject(i)));
    		    }
    		    
    		    this.updateGrid(movies);
    		    
    		    
//    		    //we update the list view 
//    		    FragmentManager fragmentManager = getFragmentManager();
//    	        fragment.updateGrid(movies);
//    	        fragmentManager.beginTransaction().commit();
    			
    		} catch (InterruptedException e) {
    			e.printStackTrace();
    		} catch (ExecutionException e) {
    			e.printStackTrace();
    		} catch (JSONException e) {
    			e.printStackTrace();
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
//            txtQuery.setText("Search Query: " + query);
 
        }
 
    }
    
    public void updateGrid(ArrayList<Movie> movieList) {
    	GridView rootView = (GridView) findViewById(R.id.allocine_grid_view);
//    	ListView rootView = (ListView) findViewById(R.id.allocine_grid_view);
        
        String[] movieListUrl = new String[movieList.size()];
        for (int i = 0; i<movieList.size();i++){
        	movieListUrl[i] = movieList.get(i).getPosterUrl();
        }
        
        ArrayList<Bitmap> images= loadImageFromURL(movieListUrl);
        
        for (int i = 0; i<images.size();i++)
        	movieList.get(i).setPoster(images.get(i));
        
//        GridView gridView = (GridView) rootView.findViewById(R.id.allocine_grid_view);
//        OnlineImageAdapter adapter = (OnlineImageAdapter) gridView.getAdapter();
//        adapter.setMovieList(movieList);
//        gridView.setAdapter(adapter);
        rootView.setAdapter(new OnlineImageAdapter(getBaseContext(), movieList));
	}
    
    

	private ArrayList<Bitmap> loadImageFromURL(String[] urls){
		try{
			ImageDownloadTask imageDownloader = new ImageDownloadTask();
			ArrayList<Bitmap> downloadedImages = imageDownloader.execute(urls).get();
			return downloadedImages;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}