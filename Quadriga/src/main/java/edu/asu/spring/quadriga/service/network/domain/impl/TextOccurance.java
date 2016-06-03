package edu.asu.spring.quadriga.service.network.domain.impl;

import edu.asu.spring.quadriga.domain.enums.ETextAccessibility;

public class TextOccurance {

    private String textUri;
    
    private ETextAccessibility access;
    public String getTextUri() {
        return textUri;
    }
    public void setTextUri(String textUri) {
        this.textUri = textUri;
    }
    
    public ETextAccessibility getAccess() {
        return access;
    }
    public void setAccess(ETextAccessibility access) {
        this.access = access;
    }
    
    
}
