package edu.osu.cse.autocomplete.objects;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class JsonObject {
	List<Movie> listOfMovies;
	
	public JsonObject(){
		
	}
	
	public JsonObject(List<Movie> listOfMovies){
		this.listOfMovies=listOfMovies;
	}
	
	
	public List<Movie> getListOfMovies() {
		return listOfMovies;
	}

	public void setListOfMovies(List<Movie> listOfMovies) {
		this.listOfMovies = listOfMovies;
	}
	
}
