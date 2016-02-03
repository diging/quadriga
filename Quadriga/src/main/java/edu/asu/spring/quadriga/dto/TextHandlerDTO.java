package edu.asu.spring.quadriga.dto;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

public class TextHandlerDTO {

    
    @Entity
    @Table(name = "tbl_textfiles")
    public class TextFileEntity implements Serializable 
    {   
        private static final long serialVersionUID = -1798070786993154676L;
        
        
    }
    
    
}
