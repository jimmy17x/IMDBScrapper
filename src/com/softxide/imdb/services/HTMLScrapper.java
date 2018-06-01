package com.softxide.imdb.services;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.softxide.imdb.exceptions.MovieNotFoundException;
import com.softxide.imdb.utils.Constants;

public class HTMLScrapper {

	private HTMLScrapper() {

	}

	public static String getMovieURL(String movieName) {

		String movieURL = null;
		
		try {
			if (movieName.trim().isEmpty()) {
				throw new MovieNotFoundException("Movie name is empty");
			}
			
			// query IMDB for movie name
			Document doc = Jsoup.connect(Constants.IMDB_HOST_QUERY + movieName).get();
			// get the table having result of queries
			Element table = doc.getElementsByClass(Constants.LIST_TABLE_CLASS_NAME).first();
			if (table == null) {
				throw new MovieNotFoundException("Movie is not available in IMDB");
			}
			// get the first table row having results from query for movie name
			Element firstRow = table.getElementsByClass(Constants.LIST_TABLE_ROW_CLASS_NAME).first();

			// get the table data for movie text
			Element movieTextElement = firstRow.getElementsByClass(Constants.LIST_TABLE_DATA_CLASS).first();
			movieURL =  movieTextElement.child(0).attr(Constants.HREF_STRING);
		} catch (MovieNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return movieURL;
	}

	public static String getMovieRating(String movieURL) {

		String movieRating = null;
		try {
			
			if (movieURL.trim().isEmpty()) {
				throw new MovieNotFoundException("Movie URL is empty");
			}
			
			// query IMDB for movie url
			Element ratingValueDiv = Jsoup.connect(Constants.IMDB_HOST + movieURL).get().getElementsByClass("ratingValue").first();
			if(ratingValueDiv == null)
				throw new MovieNotFoundException("Movie URL or Rating  is not available ");
			
			movieRating = ratingValueDiv.getElementsByAttributeValue(Constants.RATING_ATTRIB, Constants.RATING_ATTRIB_VALUE).first().ownText();
		
		
		
		}catch(MovieNotFoundException e){
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		return movieRating;
		
	}
}
