package edu.asu.spring.quadriga.profile.impl;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "items"})
@XmlRootElement(name="rss")
public class ViafReply {
	
	@XmlElementWrapper(name="channel")
	@XmlElement(name="item")
	private List<ViafReply.Items> items;
	
	public List<ViafReply.Items> getItems() {
		return items;
	}
	public void setItems(List<ViafReply.Items> items) {
		this.items = items;
	}


	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(propOrder={"title","link","pubDate","guid"})
	public static class Items {
		
		@XmlElement(required=true)
		private String title;
		@XmlElement(required=true)
		private String link;
		@XmlElement(required=true)
		private String pubDate;
		@XmlElement(required=true)
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

}
