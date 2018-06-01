package com.softxide.imdb.services;

import java.io.IOException;
import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.softxide.imdb.exceptions.MovieNotFoundException;
import com.softxide.imdb.utils.Constants;

public class Main {
	
	public static void main(String[] args) {
		
		System.out.println(" ********************* Welcome to My Movie Rating portal **********************");
		System.out.println("Enter movie name below or press -1 to exit ..");
		
		Scanner reader = new Scanner(System.in);
		
		// save movie name
		String movieName = "null" ; // some default movie name 
		
		while(true)
		{
			// save movie name
			 movieName = reader.nextLine();
			if(movieName.trim().equals("-1"))
			{
				System.out.println("Exit... ");
				break;
			};
			
			System.out.println("Searching closest match for '" + movieName+ "'.....") ;
			
			String movieURL = HTMLScrapper.getMovieURL(movieName);
						
			// get rating if url is present for that movie
			if(movieURL != null){
				
				String movieRating = HTMLScrapper.getMovieRating(movieURL);
				if(movieRating == null)
				{
					System.out.println(Constants.RETRY_MESSAGE);
					continue;
				}
				
				System.out.println("Movie URL  : " + Constants.IMDB_HOST+movieURL);
				System.out.println("Movie rating : " + movieRating);
				
			}else {
				System.out.println(Constants.RETRY_MESSAGE);
			}
		}
		
	}

}
