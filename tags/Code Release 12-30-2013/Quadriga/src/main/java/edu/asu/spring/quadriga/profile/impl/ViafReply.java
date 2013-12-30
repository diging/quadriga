package edu.asu.spring.quadriga.profile.impl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import edu.asu.spring.quadriga.jaxb.viaf.Channel;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="rss")
public class ViafReply {
	
	@XmlElement(name="channel")
	private Channel channel;
	
	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	
	/*@XmlElementWrapper(name="rss")
	@XmlElement(name="item")
	private List<ViafReply.Items> items;
	
	//@XmlPath
	public List<ViafReply.Items> getItems() {
		return items;
	} 
	public void setItems(List<ViafReply.Items> items) {
		this.items = items;
	}


	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlRootElement(name="item")
	public static class Items {
		
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
	}*/
}
