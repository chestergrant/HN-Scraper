import java.io.*;
import java.net.*;
class WebPage {
	String url;
	public WebPage(String url){
		this.url = url;
	}
	
	public String getContent() throws IOException{
		URL page = new URL(url);
        URLConnection conn = page.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        String output="";
        while ((inputLine = in.readLine()) != null) 
            output += inputLine;
        in.close();
        return output;

	}
}
