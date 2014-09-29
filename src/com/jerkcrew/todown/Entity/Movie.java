package com.jerkcrew.todown.Entity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Movie {

	String title = "";
	String originalTitle = "";
	String poster = "";
	String link = "";
	
	public Movie(JSONObject movie) {
		try {
			this.title = movie.getString("title");
			this.originalTitle = movie.getString("originalTitle");
			JSONObject moviePoster = movie.getJSONObject("poster");
			this.poster = moviePoster.getString("href");
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

	public String getPoster() {
		return poster;
	}

	public void setPoster(String poster) {
		this.poster = poster;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

}
