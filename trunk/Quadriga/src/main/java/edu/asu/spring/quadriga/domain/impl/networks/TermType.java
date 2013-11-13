//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.08.01 at 10:33:36 AM MST 
//


package edu.asu.spring.quadriga.domain.impl.networks;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.asu.spring.quadriga.service.impl.NetworkManager;



/**
 * <p>Java class for termType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="termType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice maxOccurs="unbounded">
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="creator" type="{http://www.w3.org/2001/XMLSchema}anyURI"/>
 *         &lt;element name="creation_date" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="creation_place" type="{http://www.w3.org/2001/XMLSchema}anyURI"/>
 *         &lt;element name="source_reference" type="{http://www.w3.org/2001/XMLSchema}anyURI"/>
 *         &lt;element name="interpretation" type="{http://www.w3.org/2001/XMLSchema}anyURI"/>
 *         &lt;element name="normalized_representation" type="{http://www.w3.org/2001/XMLSchema}anyURI" minOccurs="0"/>
 *         &lt;element name="printed_representation" type="{http://digitalhps.org/creationEvents-model}printedRepresentationType" minOccurs="0"/>
 *         &lt;element name="certain" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="internal_refId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="external_refId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="referenced_terms" type="{http://digitalhps.org/creationEvents-model}referencedTermsType" minOccurs="0"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "termType", propOrder = {
    "idOrCreatorOrCreationDate"
})
public class TermType {

    @XmlElementRefs({
        @XmlElementRef(name = "creation_date", namespace = "http://digitalhps.org/creationEvents-model", type = JAXBElement.class),
        @XmlElementRef(name = "external_refId", namespace = "http://digitalhps.org/creationEvents-model", type = JAXBElement.class),
        @XmlElementRef(name = "referenced_terms", namespace = "http://digitalhps.org/creationEvents-model", type = JAXBElement.class),
        @XmlElementRef(name = "certain", namespace = "http://digitalhps.org/creationEvents-model", type = JAXBElement.class),
        @XmlElementRef(name = "normalized_representation", namespace = "http://digitalhps.org/creationEvents-model", type = JAXBElement.class),
        @XmlElementRef(name = "printed_representation", namespace = "http://digitalhps.org/creationEvents-model", type = JAXBElement.class),
        @XmlElementRef(name = "creator", namespace = "http://digitalhps.org/creationEvents-model", type = JAXBElement.class),
        @XmlElementRef(name = "creation_place", namespace = "http://digitalhps.org/creationEvents-model", type = JAXBElement.class),
        @XmlElementRef(name = "source_reference", namespace = "http://digitalhps.org/creationEvents-model", type = JAXBElement.class),
        @XmlElementRef(name = "id", namespace = "http://digitalhps.org/creationEvents-model", type = JAXBElement.class),
        @XmlElementRef(name = "internal_refId", namespace = "http://digitalhps.org/creationEvents-model", type = JAXBElement.class),
        @XmlElementRef(name = "interpretation", namespace = "http://digitalhps.org/creationEvents-model", type = JAXBElement.class)
    })
    protected List<JAXBElement<?>> idOrCreatorOrCreationDate;

    private static final Logger logger = LoggerFactory
			.getLogger(TermType.class);
    /**
     * Returns the interperation object 
     * @author Lohith Dwaraka 
     * @param tt
     * @return
     */
    public String getTermInterpertation(TermType tt){
    	String interpertation ="";
    	List<JAXBElement<?>> e3 =tt.getIdOrCreatorOrCreationDate();
		Iterator <JAXBElement<?>> I2 = e3.iterator();
		while(I2.hasNext()){
			JAXBElement<?> element = (JAXBElement<?>) I2.next();
			if(element.getName().toString().contains("interpretation")){
				interpertation =element.getValue().toString();
			}
		}
    	return interpertation;
    }
    
    /**
     * Returns the term ID object 
     * @author Lohith Dwaraka 
     * @param tt
     * @return
     */
    public String getTermID(TermType tt){
    	String id ="";
    	List<JAXBElement<?>> e3 =tt.getIdOrCreatorOrCreationDate();
		Iterator <JAXBElement<?>> I2 = e3.iterator();
		while(I2.hasNext()){
			JAXBElement<?> element = (JAXBElement<?>) I2.next();
			if(element.getName().toString().contains("}id")){
				id =element.getValue().toString();
			}
		}
    	return id;
    }
    
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
     * {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * {@link JAXBElement }{@code <}{@link ReferencedTermsType }{@code >}
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     * {@link JAXBElement }{@code <}{@link PrintedRepresentationType }{@code >}
     * {@link JAXBElement }{@code <}{@link String }{@code >}
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

