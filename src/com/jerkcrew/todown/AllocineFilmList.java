package com.jerkcrew.todown;

import java.util.ArrayList;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.jerkcrew.todown.Entity.Movie;
import com.jerkcrew.todown.Modele.ImageDownloadTask;

public class AllocineFilmList extends Fragment {
	public static final String ARG_MENU_NUMBER = "menu_number";
	
    public AllocineFilmList() {
        // Empty constructor required for fragment subclasses
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	
    	View rootView = inflater.inflate(R.layout.allocine_grid_view, container, false);
        int i = getArguments().getInt(ARG_MENU_NUMBER);
        String menu = getResources().getStringArray(R.array.menu_array)[i];
//        GridView gridView = (GridView) rootView.findViewById(R.id.allocine_grid_view);
//        gridView.setAdapter(new OnlineImageAdapter(rootView.getContext(), null));
//        gridView.setAdapter(new ImageAdapter(rootView.getContext()));
        
        getActivity().setTitle(menu);
        return rootView;
    }
    
    public void updateGrid(ArrayList<Movie> movieList) {
    	GridView rootView = (GridView) this.getView();
        
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
        rootView.setAdapter(new OnlineImageAdapter(rootView.getContext(), movieList));
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
