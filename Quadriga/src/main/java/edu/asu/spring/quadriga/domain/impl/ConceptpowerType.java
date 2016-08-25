package edu.asu.spring.quadriga.domain.impl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

@XmlAccessorType(XmlAccessType.FIELD)
public class ConceptpowerType {

    @XmlValue
    private String type;
    
    @XmlAttribute(name="type_uri")
    private String type_uri;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType_uri() {
        return type_uri;
    }

    public void setType_uri(String type_uri) {
        this.type_uri = type_uri;
    }
    
    
}
