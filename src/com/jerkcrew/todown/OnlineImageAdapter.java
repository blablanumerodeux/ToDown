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
			imageView.setPadding(5,5,5,5);
		}else{
			imageView=(ImageView)convertView;
		}
		String url = this.movieList.get(position).getPoster();
		if (url !=null){
			imageView.setImageBitmap(loadImageFromURL(url));
		}else {
			imageView.setImageResource(R.drawable.sample_2);
		}
		return imageView;
	}



	private Bitmap loadImageFromURL(String url){
		try{
//			InputStream is = (InputStream) new URL(url).getContent();
			ImageDownloadTask i = new ImageDownloadTask();
			Bitmap d = i.execute(url).get();
//			CurlLike curl = new CurlLike();
////			HttpResponse response = curl.execute(url).get();
//			Bitmap d = null;
//			if(response.getStatusLine().getStatusCode() == 200){
////				InputStream is = response.getEntity().getContent();
//				String StringResponse = EntityUtils.toString(response.getEntity(), HTTP.UTF_8);
//				byte[] decodedString = Base64.decode(StringResponse,
//                        Base64.DEFAULT);
//                d = BitmapFactory.decodeByteArray(decodedString, 0,decodedString.length);
                
//				d = Drawable.createFromStream(is, "src");
//				d = BitmapFactory.decodeStream(is);
//				d = new BitmapDrawable(bit);
//			}else{
//		    	System.out.println("error 403");
//		    }
			
			return d;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
}