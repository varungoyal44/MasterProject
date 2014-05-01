/*
 * Ideally: while selecting the source interface, it should be taken care that it has the simplest architecture. 
 */

package extractInfo;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;


public class extractTest 
{
	public static void main(String args[]) throws IOException
	{
		// Element.ownText()


		// Step 1: To extract all labels and instances...
		Document doc = Jsoup.connect("http://127.0.0.1/master%20project/websites/home.php").get();
		Elements labelElements = doc.getElementsByAttributeValue("id", "label");
		Elements instanceElements = doc.getElementsByAttributeValue("id", "instance");

		// Step 2: To pair C(l,i) using single link clustering algorithm... NOTE: special Date case...
		HashMap <String, String[]> singleLinkClusterMap = new HashMap<String, String[]>();
		//singleLinkClusterMap.put(key, value)




		
		for(int i=0; i<labelElements.size(); i++)
		{
			// Keys...
			String key = labelElements.select("[tag="+i+"]").text();
			
			if (!key.toLowerCase().equals("date"))
			{
				// Values...
				Elements instanceElementsForThisKey = instanceElements.select("[tag="+i+"]");
				String[] values = new String[instanceElementsForThisKey.size()]; 
				for(int j = 0; j<instanceElementsForThisKey.size(); j++)
				{
					values[j] = instanceElementsForThisKey.remove(0).text();
				}
				singleLinkClusterMap.put(key, values);					
			}
			else
			{
				Date date = new Date();
				String modifiedDate= new SimpleDateFormat("yyyy-MM-dd").format(date);
				String[] values = {modifiedDate.toString()};
				singleLinkClusterMap.put(key, values);
			}	
		}
		
		System.out.println("label:" + singleLinkClusterMap);

		// Step 3: To create base Ontology
		
		// TEST: to fire the source page with a request query then extract the data from resulting page...

		// Step 4: To create one(or more) slave to which the base ontology and interface website address is sent to.
		// This(These) slaves will then repeat steps 1 and 2 then create their own Ontology O'

		// Step 5: The new ontology O' will then be sent back to the Master to merge with original O.
	}
}
