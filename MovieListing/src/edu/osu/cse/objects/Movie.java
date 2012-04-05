package edu.osu.cse.objects;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Movie {
	String name;
	
	public Movie(){
		
	}
	public Movie(String name) {
		super();
		this.name = name;
	}
	
	@XmlElement
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
