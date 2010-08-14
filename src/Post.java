import java.util.*;
//Stores one Post
public class Post {
	Vector dataPoint;
	public Post(){
		dataPoint = new Vector();
	}
	
	//Add a data item to the post 
	public void add(String dataItem){
		dataPoint.add(dataItem);
	}
	
	//Return a data item at a position i
	public String get(int i){
		return (String)dataPoint.get(i);
		
	}
	
	//Output the content of the post
	public String toString(){
		String output="";
		for(int i=0; i< dataPoint.size(); i++){
			output += get(i);
		}		
		return output;
	}
}
