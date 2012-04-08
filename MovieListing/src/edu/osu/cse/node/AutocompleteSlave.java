package edu.osu.cse.node;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import edu.osu.cse.datastructure.Trie;

public class AutocompleteSlave {
	private int serverPort = 0;
	private ServerSocket serverSock = null;
	private static boolean loaded = false;
	private char startCh;
	private char endCh;
	private static Trie trie = new Trie();
	
	public AutocompleteSlave(int serverPort,char start,char end){
		this.serverPort=serverPort;
		this.startCh=start;
		this.endCh=end;
		if(!loaded){
			StringBuffer buffer = new StringBuffer();
			for(char alpha=startCh;alpha<=endCh;++alpha){
				try {
					String moviesFile = "/tmp/"+alpha+"_movies.txt"; 
					FileInputStream fis = new FileInputStream(moviesFile);
					InputStreamReader isr = new InputStreamReader(fis, "ISO-8859-1");
					Reader in = new BufferedReader(isr);
					int ch;
					while ((ch = in.read()) > -1) {
						buffer.append((char)ch);
						if(((char)ch)=='\n'){
							trie.add(buffer.toString().toLowerCase());
							buffer=new StringBuffer();
						}
					}
					in.close();
				} 
				catch (IOException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}finally{
					loaded=true;
				}
			}
		}
		System.out.println("Done loading the movies database.....");
	}
	public void start(){
		Socket sock = null;
		PrintWriter out = null;
        BufferedReader in = null;
		try {
			this.serverSock = new ServerSocket(this.serverPort);
		} catch (IOException e) {
			e.printStackTrace();
		}
		while(true){
			try {
				sock=this.serverSock.accept();
				out = new PrintWriter(sock.getOutputStream(), true);
		        in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
				String prefix = in.readLine();
	            System.out.println("Prefix : "+prefix);
	            List<String> listOfStrings = trie.CompleteString(prefix);
	           // trie.dump();
	            System.out.println("Got "+listOfStrings.size());
	            StringBuffer strBuffer = new StringBuffer();
	            for(String str:listOfStrings){
	            		strBuffer.append(str.substring(0, str.length()-1)+",comma,");
	            }
	            System.out.println("Sending - "+strBuffer);
	            out.println(strBuffer.toString());
	            in.close();
	            out.close();
	            
			} catch (IOException e) {
				e.printStackTrace();
			}	
		}
	}
	
	public static void main(String[] args){
		int port = Integer.parseInt(args[0]);
		String start_character = args[1].split("-")[0];
		String end_character = args[1].split("-")[1];
		AutocompleteSlave slave = new AutocompleteSlave(port,start_character.charAt(0),end_character.charAt(0));
		slave.start();
	}
}
