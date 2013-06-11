package edu.asu.spring.quadriga.domain.implementation;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * This class would act as a bean to map to the word power rest API
 * 
 * @author  : Lohith Dwaraka
 *
 */
@XmlRootElement(name="wordpowerReply")
public class WordpowerReply {
	@XmlElement
	public DictionaryEntry dictionaryEntry;
	
	
	WordpowerReply(){
		
	}
	
 	
}
