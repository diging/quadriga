package edu.asu.spring.quadriga.profile.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import org.springframework.stereotype.Service;

@Service
public class ViafManager1 {

	public List<HashMap<String,String>> search(String item){
		
		HashMap<String,String> entry1=new HashMap<String, String>();
		HashMap<String,String> entry2=new HashMap<String, String>();
		HashMap<String,String> entry3=new HashMap<String, String>();
		
		if(item.equals("Aleichem, Sholem")){
			entry1.put("title", "Sholem-Aleykhem, 1859-1916");
			entry1.put("link", "http://viaf.org/viaf/19675893");
			entry1.put("pubDate", "Mon, 16 Sep 2013 16:15:07 GMT");
			
			entry2.put("title", "Howe, Irving, 1920-1993");
			entry2.put("link", "http://viaf.org/viaf/108241502");
			entry2.put("pubDate", "Mon, 16 Sep 2013 16:15:07 GMT");
			
			entry3.put("title", "Berkowitz, Yitzhak Dov, 1885-1967");
			entry3.put("link", "http://viaf.org/viaf/22376920");
			entry3.put("pubDate", "Mon, 14 Jun 2013 19:03:38 GMT");
			
			List<HashMap<String,String>> items = new ArrayList<HashMap<String,String>>();
			items.add(entry1);
			items.add(entry2);
			items.add(entry3);
			
			return items;
		}
		return null;
		
		
	}
}
