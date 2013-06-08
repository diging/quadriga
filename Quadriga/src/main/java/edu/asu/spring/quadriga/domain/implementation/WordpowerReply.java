package edu.asu.spring.quadriga.domain.implementation;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="wordpowerReply")
public class WordpowerReply {
	@XmlElement
	public DictionaryEntry dictionaryEntry;
	
	
	WordpowerReply(){
		
	}
	
 	
}
