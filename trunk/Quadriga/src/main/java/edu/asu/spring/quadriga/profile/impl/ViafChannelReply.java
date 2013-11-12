package edu.asu.spring.quadriga.profile.impl;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.web.client.RestTemplate;

@XmlRootElement(name="rss")
public class ViafChannelReply {
	
	/*@Inject
	@Named("restTemplate")
	RestTemplate restTemplate;*/
	
	@XmlElement(name="item")
	private List<ViafItemReply> itemList;

	public List<ViafItemReply> getItemList() {
		return itemList;
	}

	public void setItemList(List<ViafItemReply> itemList) {
		this.itemList = itemList;
	}

	
}
