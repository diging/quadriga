//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.08.01 at 10:33:36 AM MST 
//


package edu.asu.spring.quadriga.domain.implementation.networks;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;



/**
 * <p>Java class for printedRepresentationType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="printedRepresentationType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="creator" type="{http://www.w3.org/2001/XMLSchema}anyURI"/>
 *         &lt;element name="creation_date" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="creation_place" type="{http://www.w3.org/2001/XMLSchema}anyURI"/>
 *         &lt;element name="source_reference" type="{http://www.w3.org/2001/XMLSchema}anyURI"/>
 *         &lt;element name="term_part" type="{http://digitalhps.org/creationEvents-model}termPartType" maxOccurs="unbounded"/>
 *         &lt;element name="internal_refId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "printedRepresentationType", propOrder = {
    "id",
    "creator",
    "creationDate",
    "creationPlace",
    "sourceReference",
    "termPart",
    "internalRefId"
})
public class PrintedRepresentationType {

    @XmlElement(required = true)
    protected String id;
    @XmlElement(required = true)
    @XmlSchemaType(name = "anyURI")
    protected String creator;
    @XmlElement(name = "creation_date", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar creationDate;
    @XmlElement(name = "creation_place", required = true)
    @XmlSchemaType(name = "anyURI")
    protected String creationPlace;
    @XmlElement(name = "source_reference", required = true)
    @XmlSchemaType(name = "anyURI")
    protected String sourceReference;
    @XmlElement(name = "term_part", required = true)
    protected List<TermPartType> termPart;
    @XmlElement(name = "internal_refId")
    protected String internalRefId;

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
     * Gets the value of the creator property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCreator() {
        return creator;
    }

    /**
     * Sets the value of the creator property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCreator(String value) {
        this.creator = value;
    }

    /**
     * Gets the value of the creationDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getCreationDate() {
        return creationDate;
    }

    /**
     * Sets the value of the creationDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setCreationDate(XMLGregorianCalendar value) {
        this.creationDate = value;
    }

    /**
     * Gets the value of the creationPlace property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCreationPlace() {
        return creationPlace;
    }

    /**
     * Sets the value of the creationPlace property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCreationPlace(String value) {
        this.creationPlace = value;
    }

    /**
     * Gets the value of the sourceReference property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSourceReference() {
        return sourceReference;
    }

    /**
     * Sets the value of the sourceReference property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSourceReference(String value) {
        this.sourceReference = value;
    }

    /**
     * Gets the value of the termPart property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the termPart property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTermPart().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TermPartType }
     * 
     * 
     */
    public List<TermPartType> getTermPart() {
        if (termPart == null) {
            termPart = new ArrayList<TermPartType>();
        }
        return this.termPart;
    }

    /**
     * Gets the value of the internalRefId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInternalRefId() {
        return internalRefId;
    }

    /**
     * Sets the value of the internalRefId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInternalRefId(String value) {
        this.internalRefId = value;
    }


}
