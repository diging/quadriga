/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.asu.spring.quadriga.dto;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *This class represents the column mappings for projectblog table.
 * @author Pawan Mahalle
 */
/**
 * @author PawanMahalle
 *
 */
@Entity
@Table(name = "tbl_projectblog")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProjectBlogDTO.findAll", query = "SELECT pb FROM ProjectBlogDTO pb"),   })
public class ProjectBlogDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @Basic(optional = false)
    @Column(name = "projectblogid")
    private String projectBlogId;
    
    @Basic(optional = false)
    @Column(name = "title")
    private String title;
    
    @Basic(optional = false)
    @Column(name = "createdDate")
    private Date createdDate;
    
    @Basic(optional = false)
    @Column(name = "projectid")
    private String projectid;
    
    @Basic(optional = false)
    @Column(name = "author")
    private String author;
    
    @Lob
    @Basic(optional = false)
    @Column(name = "description")
    private String description;
    
    @JoinColumn(name = "projectid", referencedColumnName = "projectid",insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private ProjectDTO projectDTO;
    
    @JoinColumn(name = "author", referencedColumnName = "username",insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private QuadrigaUserDTO projectBlogAuthorDTO;
    
    public ProjectBlogDTO() {
    }

    public String getProjectBlogId() {
        return projectBlogId;
    }

    public void setProjectBlogId(String projectBlogId) {
        this.projectBlogId = projectBlogId;
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

    public String getProjectid() {
        return projectid;
    }

    public void setProjectid(String projectid) {
        this.projectid = projectid;
    }

    public ProjectDTO getProjectDTO() {
        return projectDTO;
    }

    public void setProjectDTO(ProjectDTO projectDTO) {
        this.projectDTO = projectDTO;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public QuadrigaUserDTO getProjectBlogAuthorDTO() {
        return projectBlogAuthorDTO;
    }

    public void setProjectBlogAuthorDTO(QuadrigaUserDTO projectBlogAuthorDTO) {
        this.projectBlogAuthorDTO = projectBlogAuthorDTO;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }    
    
}
