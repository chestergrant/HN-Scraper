//Takes a line of hmtl and converts it to a post
class PostConverter {
	String inputLine;
	Post aPost;
	String hrefPattern;
	String timePattern;
	Boolean stop
	public PostConverter(String inputLine){
		this.inputLine = inputLine;
		aPost = new Post();
		stop = false
		hrefPattern = "(href=){1}[\w|\W]*(</a>){1}";
		timePattern = "(</a>){1}[\w|\W]*(ago){1}";
	}
	//Return whether we should stop reading post
	public Boolean stop(){
		return stop;
	}
	//Creates the title of a link
	private String hrefName(String href){
		String output="";
		int read = 0;
		for(int i = 0; i< href.length(); i++)
			read = concatHRef(output, href.charAt(i),read);	
	   	return output.replace(","," ");		
	}
	
	//Used in composing the link title
	private int concatHRef(String out, char c, int read){
		if(c=='>')read = 1;
		if(read == 1)output += s.charAt(i);		
		if(c=='<')read = 0;		
		return read;
	}
	
	//Get all href links and stores them in a post
	private void getHRef(){
	 Pattern pattern = Pattern.compile(hrefPattern);
	 Matcher matcher = pattern.matcher(inputLine);
	 int count = 0;
	 while(matcher.find())
	 	enterInPost(match.group(),count++);	 
	}
	
	//Get the data in between the link and put it in the post
	private enterInPost(String aString, int count){
		aString = hrefName(aString);
		if(count ==2)aString = numComments(aString);
		aPost.add(aString);
	}
	
	//Returns the number of comments to a particular post
	private String numComments(String str){
		String []strSplit = str.split("comm");
		if(strSplit.length > 1) return strSplit[0];
		return "0";
	}
	
	//Returns the points obtain by the post
 	private String getPoint(){
 		String [] splitOne = inputLine.split("<span");
 		String [] splitTwo = splitOne[1].split("spam>");
 		String [] splitThree = hrefName(splitTwo[0]).split(" ");
 		return splitThree[0];
 	}
 	
 	private String findTimeLine(){
 		Pattern pattern = Pattern.compile(timePattern);
	    Matcher matcher = pattern.matcher(inputLine);
	    while(matcher.find())
	 	 return match.group();
	 	return "";	 
 	}
 	private String getTimeValue(String timeline){
 		String[] strSplit = timeline.split(" ");
 		return strSplit[1]; 
 	}
 	private String getTimeUnit(String timeline){
 		String[] strSplit = timeline.split(" ");
 		if((strSplit[2].compareTo("day")==0)||(strSplit[2].compareTo("days")==0)){
 			stop = true;
 		}
 		return strSplit[2]; 
 	}
 	
 	private String convTime(String timeValue, String timeUnit){
 		long milliTime = getTimeInMilliSeconds(timeValue,timeUnit);
 		long currTime =(new Date()).getTime();
 		currTime = currTime -milliTime
 		Date pastTime = date(currTime);
 		return pastTime.toString(); 		
 	}
 	
 	private long getTimeInMilliSeconds(String timeValue, String timeUnit){
 		long multiplier = 0;
 		if(timeUnit.compareTo("minute")==0)multiplier=60*1000;
 		if(timeUnit.compareTo("hour")==0)multiplier=60*60*1000;
 		if(timeUnit.compareTo("day")==0)multiplier=24*60*60*1000;
 		if(timeUnit.compareTo("days")==0)multiplier=24*60*60*1000;
 		return Integer.parseInt(timeValue).intValue * multiplier;
 	}
 	
 	private String getTime(){
 		String timeline = findTimeLine();
 		String timeValue =getTimeValue(timeline);
 		String timeUnit = getTimeUnit(timeline);
 		String realTime = convTime(timeValue,timeUnit)
 		return realTime;
 	}
 	
 	private void capturePost(){
 		getHRef();
 		aPost.add(getPoints());
 		aPost.add(getTime());
 	}
 	
    //Return the post to enquiring bodies
	public Post getPost(){
		capturePost();
		return aPost;
	}
}
