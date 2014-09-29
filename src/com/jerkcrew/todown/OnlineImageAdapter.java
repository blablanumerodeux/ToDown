package com.jerkcrew.todown;

import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.jerkcrew.todown.Entity.Movie;
import com.jerkcrew.todown.Modele.CurlLike;
import com.jerkcrew.todown.Modele.ImageDownloadTask;

public class OnlineImageAdapter extends BaseAdapter{

	private Context context;
	private ArrayList<Movie> movieList;
	
	public OnlineImageAdapter(Context context, ArrayList<Movie> movieList) {
		super();
		this.context = context;
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
		ImageView imageView;
		if(convertView==null)
		{
			imageView=new ImageView(context);
			imageView.setLayoutParams(new GridView.LayoutParams(100,100));
			imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//			imageView.setPadding(5,5,5,5);
		}else{
			imageView=(ImageView)convertView;
		}
		Bitmap image = this.movieList.get(position).getPoster();
		if (image !=null){
			imageView.setImageBitmap(image);
		}else {
			imageView.setImageResource(R.drawable.sample_2);
		}
		return imageView;
	}



	
	
}