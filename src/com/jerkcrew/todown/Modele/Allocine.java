package com.jerkcrew.todown.Modele;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Bitmap;
import android.util.Base64;

import com.jerkcrew.todown.R;
import com.jerkcrew.todown.Entity.Movie;

public class Allocine {

	private String partner;
	private String secretKey;
	private int page = 1;
	
	public Allocine(String partner, String secretKey) {
		super();
		this.partner = partner;
		this.secretKey = secretKey;
	}
	
	public ArrayList<Movie> searchMovie(String query, int page) {
		this.page= page;
		ArrayList<Movie> result = this.searchMovie(query);
		this.page= 1;
		return result;
	}
	
	public ArrayList<Movie> searchMovie(String query) {

        LinkedHashMap<String, String> parameters = new LinkedHashMap<String, String>();
 	    parameters.put("partner", this.partner);
 	    parameters.put("q", query);
 	    parameters.put("format", "json");
 	    parameters.put("filter", "movie");
 	    parameters.put("count", "9");
		parameters.put("page", this.page+"");
 	    
 	    String url = this.buildURL("search",parameters);

	    CurlLike curl = new CurlLike();
	    String allocineAnswer;
	    ArrayList<Movie> movies = new ArrayList<Movie>();
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
		    for (int i =0; i<movie.length();i++){
		    	movies.add(new Movie(movie.getJSONObject(i)));
		    }
		    loadPostersImages(movies);
//		    //we update the list view 
//		    FragmentManager fragmentManager = getFragmentManager();
//	        fragment.updateGrid(movies);
//	        fragmentManager.beginTransaction().commit();
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return movies;
		
	}
	
	
    private String buildURL(String method, HashMap<String, String> params){
    	
        // build the URL
        String url = "http://api.allocine.fr/rest/v3/"+method;

        //sed
        Date today = new Date(); 
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String sed = dateFormat.format(today);

        
 	    //encodage des parametres en mode URL (avec des & et des =)
 	    StringBuffer paramsHttp = new StringBuffer();
 	    for (Entry<String, String> pair : params.entrySet()) {
 	        try {
				paramsHttp.append ( URLEncoder.encode ( pair.getKey (), "UTF-8" ) + "=" );
				paramsHttp.append ( URLEncoder.encode ( pair.getValue (), "UTF-8" ) + "&" );
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
 	    }
 	    if (paramsHttp.length () > 0) {
 	        paramsHttp.deleteCharAt ( paramsHttp.length () - 1 );
 	    }
 	    
 	    //encodage en sha1 de la date et des parametres
        String toDigest=secretKey+paramsHttp+"&sed="+sed;
        MessageDigest encodeurSHA1;
		try {
			encodeurSHA1 = MessageDigest.getInstance("SHA1");
			encodeurSHA1.update(toDigest.getBytes());
			String encodedBytes = Base64.encodeToString(encodeurSHA1.digest(),Base64.NO_WRAP);
			
			//encodage en format "URL" 
			String sigSecond = "";
			try {
				sigSecond = URLEncoder.encode(encodedBytes, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				url = "ERROR : L'encodage en UTF-8 à échoué";
				e.printStackTrace();
			}
			url += "?"+paramsHttp+"&sed="+sed+"&sig="+sigSecond;
		} catch (NoSuchAlgorithmException e1) {
			url = "ERROR : L'algorithme de SHA1 n'existe pas";
			e1.printStackTrace();
		}

        return url;
    }
    
    
    private void loadPostersImages(ArrayList<Movie> movieList) {
    	String[] movieListUrl = new String[movieList.size()];
        for (int i = 0; i<movieList.size();i++){
        	movieListUrl[i] = movieList.get(i).getPosterUrl();
        }
        
        ArrayList<Bitmap> images= loadImageFromURL(movieListUrl);
        
        for (int i = 0; i<images.size();i++)
        	movieList.get(i).setPoster(images.get(i));
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