package edu.asu.spring.quadriga.profile.impl;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

//@XmlRootElement(name="item")
//@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="item")
public class Item {
	
	@XmlElement(name="title")
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
 	}

}
