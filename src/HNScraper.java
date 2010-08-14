/**
 * @(#)HNScraper.java
 *
 * HNScraper application - Scrapes New post on Hacker News
 *
 * @author Chester Grant creator of www.timeline-x.com
 * @version 1.00 2010/8/13
 */

import java.util.*;
import java.io.*;
public class HNScraper implements StorageClass {
	Vector posts;  //Store all the post scraped
	String site;   //Location of HN page being scraped
	Page first;
    Boolean stop;
    public HNScraper(String site){
    	//Initialisation
    	this.posts = new Vector();
    	this.site = site;
    	stop = false;
    	first = new Page(this.site);
    }
    //stores the post that result from the scrape
    public void store(Post aPost){
    	posts.add(aPost);
    	System.out.println(aPost);
    }
    //Scrape the posts of the site
    public void scrape(){
    	String currentSite = site;
    	Page currentPage;
    	do{
    		currentPage = new Page(currentSite);
    		currentPage.addHandle(this);   
    		currentPage.scrape(); 		
    		currentSite = currentPage.nextPage();
    		stop = currentPage.stop();   
    		stop = true; 	
    	}while((currentPage.hasMore())&&(stop==false));
    }
    //Print out all the post collect to the output file
    public void print(String output){
    	
    	try{   	
    		//Appending to the file
    	    PrintWriter out = new PrintWriter(new FileWriter(output, true));
    		for(int i = 0; i < posts.size(); i++){
    		   	out.println((Post)posts.get(i));    		   	
    		   	System.out.println((Post)posts.get(i));
    		}
    		out.close();
    	}catch(IOException ex){
    		
    	}
    }
    
    public static void main(String[] args) {
    	String site = "http://news.ycombinator.com/newest"; //URL of HN newest post
    	String output = "output.csv"; //Output the scrape
    	HNScraper hn = new HNScraper(site); //Class to do the work
    	hn.scrape(); //Scrape the HN post
    	hn.print(output); //print out the post content to a file
    }
}
