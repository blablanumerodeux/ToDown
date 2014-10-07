package com.jerkcrew.todown;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.jerkcrew.todown.Entity.Movie;

public class OnlineImageAdapter extends BaseAdapter{

	private Context context;
	private ArrayList<Movie> movieList;
	
	public OnlineImageAdapter(Context context, ArrayList<Movie> movieList) {
		super();
		this.context = context;
		if (movieList == null) 
			this.movieList = new ArrayList<Movie>();
		else 
			this.movieList = movieList;
	}

	@Override
	public int getCount() {
		return this.movieList.size();
	}

	@Override
	public Object getItem(int position) {
		return this.movieList.get(position);
	}
	
	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View gridViewItem = new View(context);
		TextView details = new TextView(context);
		ImageView imageView = new ImageView(context);
		TextView title = new TextView(context);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		if(convertView==null){
			gridViewItem = (View) inflater.inflate(R.layout.allocine_grid_item, null);
			
		}else{
			gridViewItem=(View)convertView;
		}
		imageView = (ImageView) gridViewItem.findViewById(R.id.grid_item_image);
		details = (TextView) gridViewItem.findViewById(R.id.grid_item_details);
		title = (TextView) gridViewItem.findViewById(R.id.grid_item_title);
		
//			imageView.setLayoutParams(new GridView.LayoutParams(300,400));
		imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//			imageView.setPadding(5,5,5,5);
		Bitmap image = this.movieList.get(position).getPoster();
		if (image !=null){
			imageView.setImageBitmap(image);
		}else {
			imageView.setImageResource(R.drawable.sample_2);
		}
		
		title.setText(this.movieList.get(position).getTitle());
		details.setText(this.movieList.get(position).getOriginalTitle());
		
		return gridViewItem;
	}


	public ArrayList<Movie> getMovieList() {
		return movieList;
	}

	public void setMovieList(ArrayList<Movie> movieList) {
		this.movieList = movieList;
	}
}