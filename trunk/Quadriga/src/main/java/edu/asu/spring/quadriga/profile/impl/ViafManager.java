package edu.asu.spring.quadriga.profile.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import edu.asu.spring.quadriga.profile.IViafManager;


@Service
public class ViafManager implements IViafManager {
	
	@Autowired
	@Qualifier("restTemplate")
	RestTemplate restTemplate;

	@Autowired
	@Qualifier("viafURL")
	private String viafURL;

	@Autowired
	@Qualifier("searchViafURLPath")
	private String searchViafURLPath;
	
	@Autowired
	@Qualifier("searchViafURLPath1")
	private String searchViafURLPath1;
	
	@Autowired
	@Qualifier("searchViafURLPath2")
	private String searchViafURLPath2;
	
	
	
	@Override
	public List<ViafReply.Items> search (String item, int startIndex) {
		
		List<ViafReply.Items> items = null;
		
			
			String fullUrl = viafURL + searchViafURLPath + item + searchViafURLPath1 + startIndex + searchViafURLPath2;
			ViafReply rep = (ViafReply) restTemplate
					.getForObject(fullUrl, ViafReply.class);
			items = rep.getItems();
		
		return items;
	}
	

	
}
