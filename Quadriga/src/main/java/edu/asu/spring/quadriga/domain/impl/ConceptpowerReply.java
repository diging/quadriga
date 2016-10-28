//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.06.07 at 03:52:43 PM MST 
//


package edu.asu.spring.quadriga.domain.impl;
/**
 * This is used to get the response from conceptpower
 */
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="conceptEntry" maxOccurs="unbounded">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="lemma" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="pos" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="conceptList" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="type" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="alternativeIds" maxOccurs="unbounded">
 *           &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "conceptEntry", "alternativeIds"
})
@XmlRootElement(name = "conceptpowerReply")
public class ConceptpowerReply {

    @XmlElement(required = true, namespace="http://www.digitalhps.org/")
    protected List<ConceptpowerReply.ConceptEntry> conceptEntry;

    /**
     * Gets the value of the conceptEntry property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the conceptEntry property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getConceptEntry().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ConceptpowerReply.ConceptEntry }
     * 
     * 
     */
    public List<ConceptpowerReply.ConceptEntry> getConceptEntry() {
        if (conceptEntry == null) {
            conceptEntry = new ArrayList<ConceptpowerReply.ConceptEntry>();
        }
        return this.conceptEntry;
    }
    
    public void setConceptEntry(List<ConceptpowerReply.ConceptEntry> entries) {
        this.conceptEntry = entries;
    }
    
    @XmlElement(required = true, namespace="http://www.digitalhps.org/")
    protected List<ConceptpowerReply.AlternativeIds> alternativeIds;
    
    /**
     * Gets the value of the alternativeIds property.
     * 
     * <p>
     * This accessor method returns alternate ids of concept,
     * provided in conceptEntry object
     * 
     * 
     */
    public List<ConceptpowerReply.AlternativeIds> getAlternativeIds() {
        if (alternativeIds == null) {
            alternativeIds = new ArrayList<ConceptpowerReply.AlternativeIds>();
        }
        return this.alternativeIds;
    }

    public void setAlternativeIds(List<ConceptpowerReply.AlternativeIds> alternativeIds) {
        this.alternativeIds = alternativeIds;
    }

    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="lemma" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="pos" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="conceptList" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="type" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class ConceptEntry {

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
			ConceptEntry other = (ConceptEntry) obj;
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
        protected String conceptList;
        @XmlElement(name="wordnet_id", namespace="http://www.digitalhps.org/")
        protected String wordnetId;
        
        public String getWordnetId() {
            return wordnetId;
        }

        public void setWordnetId(String wordnetId) {
            this.wordnetId = wordnetId;
        }

        @XmlElement(namespace="http://www.digitalhps.org/", type=ConceptpowerType.class)
        protected ConceptpowerType type;

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
         * Gets the value of the conceptList property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getConceptList() {
            return conceptList;
        }

        /**
         * Sets the value of the conceptList property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setConceptList(String value) {
            this.conceptList = value;
        }

        /**
         * Gets the value of the type property.
         * 
         * @return
         *     possible object is
         *     {@link Object }
         *     
         */
        public String getType() {
            return type.getType();
        }

        public String getTypeUri() {
            return type.getType_uri();
        }
        
        public void setType(String type) {
            if (this.type == null) {
                this.type = new ConceptpowerType();
            }
            this.type.setType(type);
        }

    }
    
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class AlternativeIds {
        
        @XmlElement(required = true, namespace="http://www.digitalhps.org/")
        protected ArrayList<String> id;

        /**
         * Gets the arrayList of the id property.
         *     
         */
        public ArrayList<String> getId() {
            return id;
        }

        /**
         * Sets the arrayList of the id property.
         *     
         */
        public void setId(ArrayList<String> id) {
            this.id = id;
        }
        
    }

}
