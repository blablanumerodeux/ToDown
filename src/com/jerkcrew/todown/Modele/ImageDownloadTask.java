package com.jerkcrew.todown.Modele;

import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

public class ImageDownloadTask extends AsyncTask<String, Void, Bitmap> {

	protected Bitmap doInBackground(String... urls){
		DefaultHttpClient   httpclient = new DefaultHttpClient(new BasicHttpParams());
		HttpGet httpget = new HttpGet(urls[0]);
		
		httpget.setHeader("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Ubuntu Chromium/34.0.1847.116 Chrome/34.0.1847.116 Safari/537.36");
		
		HttpResponse result = null;
		Bitmap d = null;
		try {
			result = httpclient.execute(httpget);
			InputStream is = result.getEntity().getContent();
			if (is!=null){
				d = BitmapFactory.decodeStream(is);
				if (d!=null){
					int h = 48; // height in pixels
					int w = 48; // width in pixels    
					Bitmap scaled = Bitmap.createScaledBitmap(d, h, w, true);
					d = scaled;
				}
			}
		} catch (Exception e) { 
			Log.e("unexpected", "ERROR");
			System.out.println("unexpected error");
			e.printStackTrace();
		}
		return d;
    }
}