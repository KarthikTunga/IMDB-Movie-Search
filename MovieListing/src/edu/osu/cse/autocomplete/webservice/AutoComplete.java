package edu.osu.cse.autocomplete.webservice;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.Socket;
import java.net.UnknownHostException;
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

import edu.osu.cse.autocomplete.datastructure.Trie;
import edu.osu.cse.autocomplete.node.AutocompleteShard;
import edu.osu.cse.autocomplete.objects.JsonObject;
import edu.osu.cse.autocomplete.objects.Movie;
import edu.osu.cse.autocomplete.objects.StringPrefixJson;
import edu.osu.cse.autocomplete.utils.AutocompleteConstants;



@Path("/autocomplete")
public class AutoComplete {
	
	static boolean loaded=false;
	public Client client;

	public AutoComplete(){
		this.client = new Client("5.46.187.61",10000);
	}
	
	@Path("list")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public JsonObject getMovieNames(@QueryParam("prefix") String str){
//		if(str.charAt(0)>='a' && str.charAt(0)<='e'){
//			this.client = new Client("5.130.180.248",10000);
//		}else{
//			this.client = new Client("localhost",10000);
//		}
		List<Movie> listOfMovies = new ArrayList<Movie>();
		List<String> result = new ArrayList<String>();
		List<String> completeStrings = this.client.getCompleteStrings(str);
		if(completeStrings!=null){
			for(String str1 : completeStrings){
				result.add(str1);
			}
		}
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
/**
 * This class communicates with the sharded nodes to get the
 * complete string for a given prefix
 * @author karthik
 *
 */
class Client {
	String serverHostname;
	int serverPort;
	public Client(String serverHostname,int serverPort){
		this.serverHostname=serverHostname;
		this.serverPort=serverPort;
	}
	public List<String> getCompleteStrings(String str){
		Socket sock = null;
		PrintWriter out = null;
		BufferedReader in = null;
		List<String> listOfStrings = new ArrayList<String>();
	    try {
			sock = new Socket(this.serverHostname,this.serverPort);
			out = new PrintWriter(sock.getOutputStream(), true);
	        in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
	        out.println(str);
            String result = in.readLine();
            listOfStrings = Arrays.asList((result.split(AutocompleteConstants.DELIMITER)));
            sock.close();
            
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	    return listOfStrings;
	}
}
