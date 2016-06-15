package edu.asu.spring.quadriga.domain.impl;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
 "dictionaryEntry"
})
@XmlRootElement(name = "wordpowerReply")
public class WordpowerReply {

 @XmlElement(required = true, namespace="http://www.digitalhps.org/")
 protected List<WordpowerReply.DictionaryEntry> dictionaryEntry;



 public List<WordpowerReply.DictionaryEntry> getDictionaryEntry() {	
     if (dictionaryEntry == null) {
         dictionaryEntry = new ArrayList<WordpowerReply.DictionaryEntry>();
     }
     return this.dictionaryEntry;
 }
 public void SetDictionaryEntry(List<WordpowerReply.DictionaryEntry> dictionaryEntry) {
     this.dictionaryEntry=dictionaryEntry;
 }


 
	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name = "", propOrder = {
		"id",
		"lemma",
		"pos",
		"description",
		"vocabulary"
	})
 public static class DictionaryEntry {

     @Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((id == null) ? 0 : id.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			DictionaryEntry other = (DictionaryEntry) obj;
			if (id == null) {
				if (other.id != null)
					return false;
			} else if (!id.equals(other.id))
				return false;
			return true;
		}

		@XmlElement(required = true, namespace="http://www.digitalhps.org/")
     protected String id;
     @XmlElement(required = true, namespace="http://www.digitalhps.org/")
     protected String lemma;
     @XmlElement(required = true, namespace="http://www.digitalhps.org/")
     protected String pos;
     @XmlElement(required = true, namespace="http://www.digitalhps.org/")
     protected String description;
     @XmlElement(required = true, namespace="http://www.digitalhps.org/")
     protected String vocabulary;

     /**
      * Gets the value of the id property.
      * 
      * @return
      *     possible object is
      *     {@link String }
      *     
      */
     public String getId() {
         return id;
     }

     /**
      * Sets the value of the id property.
      * 
      * @param value
      *     allowed object is
      *     {@link String }
      *     
      */
     public void setId(String value) {
         this.id = value;
     }

     /**
      * Gets the value of the lemma property.
      * 
      * @return
      *     possible object is
      *     {@link String }
      *     
      */
     public String getLemma() {
         return lemma;
     }

     /**
      * Sets the value of the lemma property.
      * 
      * @param value
      *     allowed object is
      *     {@link String }
      *     
      */
     public void setLemma(String value) {
         this.lemma = value;
     }

     /**
      * Gets the value of the pos property.
      * 
      * @return
      *     possible object is
      *     {@link String }
      *     
      */
     public String getPos() {
         return pos;
     }

     /**
      * Sets the value of the pos property.
      * 
      * @param value
      *     allowed object is
      *     {@link String }
      *     
      */
     public void setPos(String value) {
         this.pos = value;
     }

     /**
      * Gets the value of the description property.
      * 
      * @return
      *     possible object is
      *     {@link String }
      *     
      */
     public String getDescription() {
         return description;
     }

     /**
      * Sets the value of the description property.
      * 
      * @param value
      *     allowed object is
      *     {@link String }
      *     
      */
     public void setDescription(String value) {
         this.description = value;
     }

     /**
      * Gets the value of the vocabulary property.
      * 
      * @return
      *     possible object is
      *     {@link String }
      *     
      */
     public String getVocabulary() {
         return vocabulary;
     }

     /**
      * Sets the value of the vocabulary property.
      * 
      * @param value
      *     allowed object is
      *     {@link String }
      *     
      */
     public void setVocabulary(String value) {
         this.vocabulary = value;
     }


 }

}
