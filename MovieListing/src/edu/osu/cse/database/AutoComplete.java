package edu.osu.cse.database;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import edu.osu.cse.datastructure.Trie;
import edu.osu.cse.objects.JsonObject;
import edu.osu.cse.objects.Movie;
import edu.osu.cse.objects.StringPrefixJson;


@Path("/autocomplete")
public class AutoComplete {
	
	static boolean loaded=false;
	static Trie trie = new Trie();

	public AutoComplete(){
		if(!loaded){
			StringBuffer buffer = new StringBuffer();
			try {
				FileInputStream fis = new FileInputStream("/home/karthik/movies-list.txt/movies.txt");
				InputStreamReader isr = new InputStreamReader(fis, "ISO-8859-1");
				Reader in = new BufferedReader(isr);
				int ch;
				while ((ch = in.read()) > -1) {
					buffer.append((char)ch);
					if(((char)ch)=='\n'){
						this.trie.add(buffer.toString().toLowerCase());
						buffer=new StringBuffer();
					}
				}
				in.close();
				this.trie.dump();
			}catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				loaded=true;
			}
		}
	}
	
	@Path("list")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public JsonObject getMovieNames(@QueryParam("prefix") String str){
		List<Movie> listOfMovies = new ArrayList<Movie>();
		List<String> result = trie.CompleteString(str.toLowerCase());
		result.add("");
		for(String string:result){
			listOfMovies.add(new Movie(string));
		}
		return new JsonObject(listOfMovies);
	}
	
	@Path("pref")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public StringPrefixJson done(StringPrefixJson stringPrefix){
		return new StringPrefixJson(stringPrefix.getPrefix());
	}
}
