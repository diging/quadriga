//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.08.01 at 10:33:36 AM MST 
//


package edu.asu.spring.quadriga.domain.impl.networks;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for creationEvent complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="creationEvent">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice maxOccurs="unbounded">
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="creator" type="{http://www.w3.org/2001/XMLSchema}anyURI"/>
 *         &lt;element name="creation_date" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="creation_place" type="{http://www.w3.org/2001/XMLSchema}anyURI"/>
 *         &lt;element name="source_reference" type="{http://www.w3.org/2001/XMLSchema}anyURI"/>
 *         &lt;element name="interpretation_creator" type="{http://www.w3.org/2001/XMLSchema}anyURI"/>
 *         &lt;element name="refId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="internal_refId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "creationEvent")
@XmlSeeAlso({
    AppellationEventType.class,
    RelationEventType.class
})
public abstract class CreationEvent {

    @XmlElement(name = "source_reference", namespace = "http://digitalhps.org/creationEvents-model", type = String.class)
    private String sourceReference;
    
    @XmlElement(name = "creation_date", namespace = "http://digitalhps.org/creationEvents-model", type = String.class)
    private String creationDate;
    
    @XmlElement(name = "internal_refId", namespace = "http://digitalhps.org/creationEvents-model", type = String.class)
    private String internalRefId;
    
    @XmlElement(name = "creator", namespace = "http://digitalhps.org/creationEvents-model", type = String.class)
    private String creator;
    
    @XmlElement(name = "interpretation_creator", namespace = "http://digitalhps.org/creationEvents-model", type = String.class)
    private String interpretationCreator;
    
    @XmlElement(name = "creation_place", namespace = "http://digitalhps.org/creationEvents-model", type = String.class)
    private String creationPlace;
    
    @XmlElement(name = "refId", namespace = "http://digitalhps.org/creationEvents-model", type = String.class)
    private String refId;
    
    @XmlElement(name = "id", namespace = "http://digitalhps.org/creationEvents-model", type = String.class)
    private String id;

    public String getSourceReference() {
        return sourceReference;
    }

    public void setSourceReference(String sourceReference) {
        this.sourceReference = sourceReference;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getInternalRefId() {
        return internalRefId;
    }

    public void setInternalRefId(String internalRefId) {
        this.internalRefId = internalRefId;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getInterpretationCreator() {
        return interpretationCreator;
    }

    public void setInterpretationCreator(String interpretationCreator) {
        this.interpretationCreator = interpretationCreator;
    }

    public String getCreationPlace() {
        return creationPlace;
    }

    public void setCreationPlace(String creationPlace) {
        this.creationPlace = creationPlace;
    }

    public String getRefId() {
        return refId;
    }

    public void setRefId(String refId) {
        this.refId = refId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    
}
