package edu.asu.spring.quadriga.profile.impl;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="channel")
@XmlAccessorType(XmlAccessType.FIELD)
public class Items {
	
	@XmlElement(name="item")
	private List<Item> item;
	
	@XmlElement(name="title")
	private String title;
	
	@XmlElement(name="opensearch")	
	private Integer opensearch;
	
	@XmlElement(name="link")
	private String link;
	
	@XmlElement(name="description")
	private String description;
	
	
	public Integer getOpensearch() {
		return opensearch;
	}

	public void setOpensearch(Integer opensearch) {
		this.opensearch = opensearch;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Item> getItems() {
		return item;
	}

	//@XmlElement(name="item")
	public void setItems(List<Item> item) {
		this.item = item;
	}
	
/*	@XmlElement(name="recordSchema", namespace="http://www.loc.gov/zing/srw/")
	private String recordSchema;
	*/
	/*@XmlElement(name="title")
 	private String title;
 	@XmlElement(name="link")
 	private String link;
 	@XmlElement(name="pubDate")
 	private String pubDate;
 	@XmlElement(name="guid")
 	private String guid;
 	
 	public String getTitle() {
 		return title;
 	}
 	public void setTitle(String title) {
 		this.title = title;
 	}
 	public String getLink() {
 		return link;
 	}
 	public void setLink(String link) {
 		this.link = link;
 	}
 	public String getPubDate() {
 		return pubDate;
 	}
 	public void setPubDate(String pubDate) {
 		this.pubDate = pubDate;
 	}
 	public String getGuid() {
 		return guid;
 	}
 	public void setGuid(String guid) {
 		this.guid = guid;
 	}*/
 
}
