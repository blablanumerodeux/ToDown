package com.jerkcrew.todown.Modele;

import java.io.InputStream;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

public class ImageDownloadTask extends AsyncTask<String, Void, ArrayList<Bitmap>> {

	protected ArrayList<Bitmap> doInBackground(String... urls){
		DefaultHttpClient   httpclient = new DefaultHttpClient(new BasicHttpParams());
		ArrayList<Bitmap> result = new ArrayList<Bitmap>(); 
		
		for(String url : urls){
			if (!url.equals("")){
				HttpGet httpget = new HttpGet(url);
				httpget.setHeader("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Ubuntu Chromium/34.0.1847.116 Chrome/34.0.1847.116 Safari/537.36");
				
				HttpResponse httpResponse = null;
				Bitmap decodedStream = null;
				try {
					httpResponse = httpclient.execute(httpget);
					InputStream is = httpResponse.getEntity().getContent();
					if (is!=null){
						decodedStream = BitmapFactory.decodeStream(is);
						if (decodedStream!=null){
	//						int h = 48; // height in pixels
	//						int w = 48; // width in pixels    
	//						Bitmap scaled = Bitmap.createScaledBitmap(decodedStream, h, w, true);
	//						decodedStream = scaled;
							result.add(decodedStream);
						}
					}
				} catch (Exception e) { 
					Log.e("unexpected", "ERROR");
					e.printStackTrace();
				}
			}else {
				result.add(null);
			}
		}
		return result;
    }
}