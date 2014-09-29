package com.jerkcrew.todown;

import java.util.ArrayList;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.jerkcrew.todown.Entity.Movie;

public class AllocineFilmList extends Fragment {
	public static final String ARG_MENU_NUMBER = "menu_number";
	
    public AllocineFilmList() {
        // Empty constructor required for fragment subclasses
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	
    	View rootView = inflater.inflate(R.layout.allocine_list_film, container, false);
        int i = getArguments().getInt(ARG_MENU_NUMBER);
        String menu = getResources().getStringArray(R.array.menu_array)[i];
        GridView gridView = (GridView) rootView.findViewById(R.id.allocine_list_film);
        gridView.setAdapter(new ImageAdapter(rootView.getContext()));
        
        /*String[] values = new String[] { "Android", "iPhone", "WindowsMobile",
                "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
                "Linux", "OS/2" };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, values);
            setListAdapter(adapter);*/
        /*int imageId = getResources().getIdentifier(menu.toLowerCase(Locale.getDefault()),
                        "drawable", getActivity().getPackageName());
        ((ImageView) rootView.findViewById(R.id.image)).setImageResource(imageId);*/
        getActivity().setTitle(menu);
        return rootView;
    }
    
    public void updateGrid(ArrayList<Movie> movieList) {
    	View rootView = this.getView();
        GridView gridView = (GridView) rootView.findViewById(R.id.allocine_list_film);
        gridView.setAdapter(new OnlineImageAdapter(rootView.getContext(), movieList));
	}
}
