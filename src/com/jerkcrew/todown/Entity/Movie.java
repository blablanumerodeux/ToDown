package com.jerkcrew.todown.Entity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Bitmap;

public class Movie {

	String title = "";
	String originalTitle = "";
	String posterUrl = "";
	String link = "";
	Bitmap poster = null;
	
	public Movie(JSONObject movie) {
		try {
			this.title = movie.getString("title");
			this.originalTitle = movie.getString("originalTitle");
			JSONObject moviePoster = movie.getJSONObject("poster");
			
			this.posterUrl = "http://fr.web.img"+((int) (Math.random() * 6)+1)+".acsta.net/r_x_160"+moviePoster.getString("path"); //moviePoster.getString("href");
			JSONArray movieLinks = movie.getJSONArray("link");
			JSONObject firstMovieLink = movieLinks.getJSONObject(0);
			this.link = firstMovieLink.getString("href");
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getOriginalTitle() {
		return originalTitle;
	}

	public void setOriginalTitle(String originalTitle) {
		this.originalTitle = originalTitle;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getPosterUrl() {
		return posterUrl;
	}

	public void setPosterUrl(String posterUrl) {
		this.posterUrl = posterUrl;
	}

	public Bitmap getPoster() {
		return poster;
	}

	public void setPoster(Bitmap poster) {
		this.poster = poster;
	}


}
