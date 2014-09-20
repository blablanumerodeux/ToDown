package com.jerkcrew.todown.Modele;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.os.AsyncTask;

public class CurlLike extends AsyncTask<String, Void, String> {

	  //this method doesn't work with a huge request on an only one line  
//    protected InputStream doInBackground(String... urls) {
//    	//connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.9.2.3) Gecko/20100401"); // Do as if you're using Firefox 3.6.3.
//		URLConnection connection;
//		InputStream response = null;
//		try {
//			connection = new URL(urls[0]).openConnection();
//			connection.setRequestProperty("Accept-Charset", "UTF-8");
//			response = connection.getInputStream();
////			System.out.println(connection.getContentLength());
////			System.out.println(connection.getContentLength());
//		} catch (MalformedURLException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return response;
//    }
    
    protected String doInBackground(String... urls) {
		DefaultHttpClient   httpclient = new DefaultHttpClient(new BasicHttpParams());
		HttpGet httpget = new HttpGet(urls[0]);
		
		//we change the user agent. This is really important, without this the request doesn't work 
		httpget.setHeader("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Ubuntu Chromium/34.0.1847.116 Chrome/34.0.1847.116 Safari/537.36");
//    	private String user_agent = "Dalvik/1.6.0 (Linux; U; Android 4.2.2; Nexus 4 Build/JDQ39E";
		
		// Depends on your web service
//		httppost.setHeader("Content-type", "application/json");
		
		String result = null;
		try {
		    HttpResponse response = httpclient.execute(httpget);
		    if(response.getStatusLine().getStatusCode() == 200){
	            
		    	// Connection was established. Get the content.
			    HttpEntity entity = response.getEntity();
			
			    result = EntityUtils.toString(entity, HTTP.UTF_8);
		    }else{
		    	result = "error 403";
		    }
		} catch (Exception e) { 
			result = "unexpected error";
		}
		return result;
    }
}
