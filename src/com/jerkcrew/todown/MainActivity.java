package com.jerkcrew.todown;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;

import com.jerkcrew.todown.Entity.Movie;
import com.jerkcrew.todown.Modele.Allocine;
import com.jerkcrew.todown.Modele.CurlLike;
import com.jerkcrew.todown.Modele.XmlParser;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.SearchManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.JsonReader;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {
    private DrawerLayout drawerLayout;
    private ListView drawerListView;
//    private GridView drawerGridView;
    private ActionBarDrawerToggle drawerToggle;

    private CharSequence drawerTitle;
    private CharSequence mTitle;
    private String[] leftMenuTitles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTitle = drawerTitle = getTitle();
        leftMenuTitles = getResources().getStringArray(R.array.menu_array);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerListView = (ListView) findViewById(R.id.left_drawer);
        //drawerGridView = (GridView) findViewById(R.id.gridview);

        // set up the drawer's list view with items and click listener
        drawerListView.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, leftMenuTitles));
        drawerListView.setOnItemClickListener(new DrawerItemClickListener());
        // enable ActionBar app icon to behave as action to toggle nav drawer
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        // ActionBarDrawerToggle ties together the proper interactions
        // between the sliding drawer and the action bar app icon
        drawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                drawerLayout,          /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
                ) {
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(drawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        drawerLayout.setDrawerListener(drawerToggle);

        if (savedInstanceState == null) {
            selectItem(0);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
         // The action bar home/up action should open or close the drawer.
         // ActionBarDrawerToggle will take care of this.
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action buttons
        switch(item.getItemId()) {
	        case R.id.action_settings:

	        	LinkedHashMap<String, String> data = new LinkedHashMap<String, String>();
	     	    data.put("partner", ""+getResources().getString(R.string.ALLOCINE_PARTNER_KEY));
	     	    data.put("q", "the");
	     	    data.put("format", "json");
	     	    data.put("filter", "movie");
	     	    
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
				    ArrayList<Movie> movies = new ArrayList<Movie>();
				    for (int i =0; i<movie.length();i++){
				    	movies.add(new Movie(movie.getJSONObject(i)));
				    }
					
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				} catch (JSONException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
				
	        	Toast.makeText(this, "Hey ! ", Toast.LENGTH_LONG).show();
	        	
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
        }
	}
    
    /* The click listner for ListView in the navigation drawer */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    private void selectItem(int menuNumber) {
        // update the main content by replacing fragments
        Fragment fragment = new AllocineFilmList();
        Bundle args = new Bundle();
        args.putInt(AllocineFilmList.ARG_MENU_NUMBER, menuNumber);
        fragment.setArguments(args);

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

        // update selected item and title, then close the drawer
        drawerListView.setItemChecked(menuNumber, true);
        setTitle(leftMenuTitles[menuNumber]);
        drawerLayout.closeDrawer(drawerListView);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        drawerToggle.onConfigurationChanged(newConfig);
    }
}
