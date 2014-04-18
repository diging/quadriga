//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.08.01 at 10:33:36 AM MST 
//


package edu.asu.spring.quadriga.domain.implementation.networks;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for termPartType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="termPartType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice maxOccurs="10" minOccurs="7">
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="creator" type="{http://www.w3.org/2001/XMLSchema}anyURI"/>
 *         &lt;element name="creation_date" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="creation_place" type="{http://www.w3.org/2001/XMLSchema}anyURI"/>
 *         &lt;element name="source_reference" type="{http://www.w3.org/2001/XMLSchema}anyURI"/>
 *         &lt;element name="position" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="expression" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="normalization" type="{http://www.w3.org/2001/XMLSchema}anyURI"/>
 *         &lt;element name="format" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="formatted_pointer" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
@XmlType(name = "termPartType", propOrder = {
    "idOrCreatorOrCreationDate"
})
public class TermPartType {

    @XmlElementRefs({
        @XmlElementRef(name = "formatted_pointer", namespace = "http://digitalhps.org/creationEvents-model", type = JAXBElement.class),
        @XmlElementRef(name = "normalization", namespace = "http://digitalhps.org/creationEvents-model", type = JAXBElement.class),
        @XmlElementRef(name = "creation_date", namespace = "http://digitalhps.org/creationEvents-model", type = JAXBElement.class),
        @XmlElementRef(name = "format", namespace = "http://digitalhps.org/creationEvents-model", type = JAXBElement.class),
        @XmlElementRef(name = "creation_place", namespace = "http://digitalhps.org/creationEvents-model", type = JAXBElement.class),
        @XmlElementRef(name = "id", namespace = "http://digitalhps.org/creationEvents-model", type = JAXBElement.class),
        @XmlElementRef(name = "position", namespace = "http://digitalhps.org/creationEvents-model", type = JAXBElement.class),
        @XmlElementRef(name = "internal_refId", namespace = "http://digitalhps.org/creationEvents-model", type = JAXBElement.class),
        @XmlElementRef(name = "source_reference", namespace = "http://digitalhps.org/creationEvents-model", type = JAXBElement.class),
        @XmlElementRef(name = "expression", namespace = "http://digitalhps.org/creationEvents-model", type = JAXBElement.class),
        @XmlElementRef(name = "creator", namespace = "http://digitalhps.org/creationEvents-model", type = JAXBElement.class)
    })
    protected List<JAXBElement<?>> idOrCreatorOrCreationDate;

    /**
     * Gets the value of the idOrCreatorOrCreationDate property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the idOrCreatorOrCreationDate property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getIdOrCreatorOrCreationDate().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * {@link JAXBElement }{@code <}{@link BigInteger }{@code >}
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * 
     */
    public List<JAXBElement<?>> getIdOrCreatorOrCreationDate() {
        if (idOrCreatorOrCreationDate == null) {
            idOrCreatorOrCreationDate = new ArrayList<JAXBElement<?>>();
        }
        return this.idOrCreatorOrCreationDate;
    }

}