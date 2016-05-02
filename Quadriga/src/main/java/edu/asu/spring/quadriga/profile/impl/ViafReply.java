package edu.asu.spring.quadriga.profile.impl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import edu.asu.spring.quadriga.jaxb.viaf.Channel;

/**
 * 
 * jaxb class for parsing xml returned by calling viaf service 
 * 
 * @author rohit pendbhaje
 *
 */
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

}
