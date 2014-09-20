package Entity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Movie {

	String title;
	String originalTitle;
	String poster;
	String link;
	
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

}
