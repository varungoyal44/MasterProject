package parseWebpage;

import java.io.File;
import java.io.IOException;

import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;

public class ParseFromDocument 
{
	public static void main(String args[]) throws IOException
	{
		File input = new File("src/form1.html");
		Document doc = Jsoup.parse(input, "UTF-8", "http://example.com/");
		
		Element content = doc.getElementById("label");
		System.out.println("doc: "+content);
//		Elements links = content.getElementsByTag("a");
//		for (Element link : links) {
//		  String linkHref = link.attr("href");
//		  String linkText = link.text();
//		}
	}
}
