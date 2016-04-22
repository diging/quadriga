package edu.asu.spring.quadriga.dto;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "tbl_publicpage")

public class PublicPageDTO implements Serializable 
{
    private static final long serialVersionUID = 1L;
    
    @Basic(optional = false)
    @Column(name = "title")
    private String title;
    @Basic(optional = false)
    @Column(name = "description")
    private String description;
    @Id
    @Basic(optional = false)
    @Column(name = "order")
    private int order;

   
	public PublicPageDTO() {
    }

    public PublicPageDTO(String title, String description, int order) {
        this.title = title;
        this.description = description;
        this.order = order;
            }


    public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}
}