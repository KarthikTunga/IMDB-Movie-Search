package edu.osu.cse.datastructure;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

/**
 * @author karthik
 *
 */

public class Trie {
	Node headNode;

	public Trie() {
		this.headNode = new Node();
	}

	public Trie(Node headNode) {
		super();
		this.headNode = headNode;
	}

	/**
	 * Public method for adding a word in the Trie.
	 * @param str
	 */
	public void add(String str) throws Exception{
		addNode(headNode, new StringBuffer(str.toLowerCase()), 0);
	}
	
	
	/**
	 * Private method for adding a word to the Trie.
	 * @param currentNode
	 * @param sb
	 * @param index
	 * @throws Exception 
	 */
	
	private void addNode(Node currentNode, StringBuffer sb, int index) throws Exception {
	//	System.out.println(sb.length() +" , "+index);
		try{
			char ch = sb.charAt(index);
			if (index == sb.length() - 1) {
				if (!currentNode.links.containsKey(ch)) {
					Node node = new Node(ch, true,new Hashtable<Character,Node>());
					currentNode.links.put(ch,node);
				} else {
					currentNode.links.get(ch).isEnd = true;
				}
				return;
			} else {
				if (!currentNode.links.containsKey(ch)) {
					Node node = new Node(ch, false,new Hashtable<Character,Node>());
					currentNode.links.put(ch,node);
				}
				addNode(currentNode.links.get(ch), sb, index + 1);
			}
		}catch(Exception ex){
			throw new Exception(ex);
		}
	}
	
	/**
	 * Public interface for printing all the words in the Trie.
	 */
	public void dump() {
		dump(this.headNode, new StringBuffer());
	}
	
	/**
	 * Private method that prints all the words in the Trie.
	 * @param currentNode
	 * @param sb
	 */
	private void dump(Node currentNode, StringBuffer sb) {
		if (currentNode.isEnd) {
			System.out.println(sb);
		}
		Set<Character> keySet = currentNode.links.keySet();
		for(Character ch:keySet){
			sb.append(ch);
			dump(currentNode.links.get(ch),sb);
			sb.deleteCharAt(sb.length() - 1);
		}
	}
	
	/**
	 * This method  is the public interface for the generating the 
	 * complete strings from a given prefix
	 * @param prefix
	 * @return List<String>
	 */
	public List<String> CompleteString(String prefix) {
		System.out.println("prefis is :"+prefix+"#####");
		List<String> result = new ArrayList<String>();
		Node tmp = this.headNode;
		Set<Character> keySet = tmp.links.keySet();
		for(char s :keySet){
			System.out.println("keyset : "+s);
		}
		for (int i = 0; i < prefix.length(); ++i) {
			if (!tmp.links.containsKey(prefix.charAt(i))) {
				System.out.println("doesn't have "+prefix.charAt(i));
				return result;
			}
			tmp = tmp.links.get(prefix.charAt(i));
		}
		System.out.println("going to autocomplete...");
		getAutocompleteStrings(tmp, result, new StringBuffer(prefix.toLowerCase()));
		return result;
	}

	/**
	 * Private recursive method that performs the actual task of generating 
	 * the complete strings from a prefix by parsing the Trie.
	 * @param currentNode
	 * @param completeStrings
	 * @param prefix
	 */
	private void getAutocompleteStrings(Node currentNode,
			List<String> completeStrings, StringBuffer prefix) {
		
		if (completeStrings.size() >= 10)
			return;
		if (currentNode.isEnd) {
			completeStrings.add(prefix.toString());
		}
		Set<Character> keySet = currentNode.links.keySet();
		for(Character ch:keySet){
			prefix.append(ch);
			getAutocompleteStrings(currentNode.links.get(ch), completeStrings, prefix);
			prefix.deleteCharAt(prefix.length() - 1);
		}
	}
	
	/**
	 * Main method for testing the Trie data structure.
	 * @param args
	 */
	public static void main(String[] args) {
		Trie t = new Trie();
		
		StringBuffer buffer = new StringBuffer();
	    try {
	        FileInputStream fis = new FileInputStream("/home/karthik/movies-list.txt/movies.txt");
	        InputStreamReader isr = new InputStreamReader(fis, "ISO-8859-1");
	        Reader in = new BufferedReader(isr);
	        int ch;
	        while ((ch = in.read()) > -1) {
	        	buffer.append((char)ch);
	        	char a = (char)ch;
	        	if(((char)ch)=='\n'){
	        		t.add(buffer.toString().toLowerCase());
					buffer=new StringBuffer();
				}
	        }
	        in.close();
	    } 
	    catch (IOException e) {
	        e.printStackTrace();
	    } catch (Exception e) {
			e.printStackTrace();
		}
	    t.dump();
	    System.out.println("##################################");
	    List<String> res = t.CompleteString("a buried");
		for (String str : res) {
			System.out.println(str);
		}
		System.out.println("##################################");
	}
}
/**
 * Each node in the Trie.
 * @author karthik
 *
 */
class Node {
	char alphabet;
	boolean isEnd;
	Hashtable<Character,Node> links = new Hashtable<Character,Node>();

	public Node() {

	}

	public Node(char alphabet, boolean isEnd, Hashtable<Character,Node> links) {
		super();
		this.alphabet = alphabet;
		this.isEnd = isEnd;
		this.links = links;
	}

	public char getAlphabet() {
		return alphabet;
	}

	public void setAlphabet(char alphabet) {
		this.alphabet = alphabet;
	}

	public boolean isEnd() {
		return isEnd;
	}

	public void setEnd(boolean isEnd) {
		this.isEnd = isEnd;
	}

	public Hashtable<Character,Node> getLinks() {
		return links;
	}

	public void setLinks(Hashtable<Character,Node> links) {
		this.links = links;
	}

}
