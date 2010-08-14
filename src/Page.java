//Used in scraping the post off of a page
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.io.*;
public class Page {
	StorageClass handle;
	String next;
	String pageURL;
	String nextPattern;
	String postPattern;
	String urlContent;
	String base;
	Boolean stop;
	WebPage aURL;
	public Page(String pageURL){
		nextPattern = "(href){1}[\\w|\\W]*?(More){1}";
		postPattern = "(class=\"title\"><a){1}[\\w|\\W]*?(((comment)|(discuss))</a){1}";
		this.pageURL = pageURL;		
		handle = null;
		this.next = null;				
		stop = false;
		base = getBase();
		obtainContent();	
		getNext();
		System.out.println(next);		
			
	}
	private void obtainContent(){
		aURL = new WebPage(pageURL);
		try{
			urlContent = aURL.getContent();		
		}catch(IOException ex){
			System.out.println("Error reading pagex");
			System.exit(1);
		}
	}
	private void stripPost(String aLine){
		//System.out.println(aLine);
		//System.exit(1);
		PostConverter conv = new PostConverter(aLine);
		stop = conv.stop();
		if(!stop) handle.store(conv.getPost());
	}
	//Scrape the page for it's post
	public void scrape(){
	 Pattern pattern = Pattern.compile(postPattern);
	 Matcher matcher = pattern.matcher(this.urlContent);
	 while((matcher.find())&&(stop == false)){
	 	stripPost(matcher.group());
	 }
	}
	public Boolean stop(){
		return stop;
	}
	//Return the base url of the this page
	public String getBase(){
		String ans [] = pageURL.split(".com");
		return ans[0]+".com";
	}
	
	//Set the value of next
	private String setNext(String s){
		String output="";
		int read = 0;
		for(int i = 0; i< s.length(); i++){
			read = concatNext(output, s.charAt(i),read);			
		}
		return output;
	}
	
	//Concatenate the output of Next
	private int concatNext(String output, char c, int read){
		if(c=='/')read = 1;
		if(read == 1)output += c;		
		if(c=='"')read = 0;		
		return read;
	}
	
	//Get the next page
	public void getNext(){
	 Pattern pattern = Pattern.compile(nextPattern);
	 Matcher matcher = pattern.matcher(this.urlContent);
	 while(matcher.find()){
	 	next = base + setNext(matcher.group());
	 }
	   	
	}
	
	//Returns whether the page has more pages afterwards
	public Boolean hasMore(){
		if(this.next == null){
			return false;
		}
		return true;
	}
	//Return the next page to be scraped
	public String nextPage(){
		return next;
	}
	//Adds class to be called when we are storing stuff
	public void addHandle(StorageClass handle){
		this.handle = handle;
	}
	
}
