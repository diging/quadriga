package edu.asu.spring.quadriga.domain.enums;

public enum ETextAccessibility {

    PUBLIC("PUBLIC"),
    PRIVATE("PRIVATE");

    private String id;
    
    private ETextAccessibility(String id)
    {
        this.id = id;
    }

    public String getId() 
    {
        return id;
    }
    
    
}
