package edu.osu.cse.node;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Client {
	String serverHostname;
	int serverPort;
	public Client(String serverHostname,int serverPort){
		this.serverHostname=serverHostname;
		this.serverPort=serverPort;
	}
	public void getCompleteStrings(){
		Socket sock = null;
		PrintWriter out = null;
		BufferedReader in = null;
	    
	    try {
			sock = new Socket(this.serverHostname,this.serverPort);
			out = new PrintWriter(sock.getOutputStream(), true);
	        in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
	        out.println("b");
            List<String> listOfStrings = new ArrayList<String>();
            String result = in.readLine();
            listOfStrings = Arrays.asList((result.split(",comma,")));
            sock.close();
            for(String str:listOfStrings){
            	System.out.println(str);
            }
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}   
	}
	public static void main(String[] args){
		String hostname = args[0];
		int port = Integer.parseInt(args[1]);
		System.out.println("Connecting to "+hostname+":"+port);
		Client client = new Client(hostname,port);
		client.getCompleteStrings();
		
	}
}
