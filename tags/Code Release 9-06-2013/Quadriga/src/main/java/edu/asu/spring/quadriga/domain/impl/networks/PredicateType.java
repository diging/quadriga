//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.08.01 at 10:33:36 AM MST 
//


package edu.asu.spring.quadriga.domain.impl.networks;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for predicateType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="predicateType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="appellation_event" type="{http://digitalhps.org/creationEvents-model}appellationEventType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "predicateType", propOrder = {
    "appellationEvent"
})
public class PredicateType {

    @XmlElement(name = "appellation_event", required = true)
    protected AppellationEventType appellationEvent;

    /**
     * Gets the value of the appellationEvent property.
     * 
     * @return
     *     possible object is
     *     {@link AppellationEventType }
     *     
     */
    public AppellationEventType getAppellationEvent() {
        return appellationEvent;
    }

    /**
     * Sets the value of the appellationEvent property.
     * 
     * @param value
     *     allowed object is
     *     {@link AppellationEventType }
     *     
     */
    public void setAppellationEvent(AppellationEventType value) {
        this.appellationEvent = value;
    }

}