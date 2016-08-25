package edu.asu.spring.quadriga.dto;

import java.io.Serializable;
import java.util.Date;

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
 * This class represents the column mappings for <code>tbl_projectblogentry</code>
 * table.
 * 
 * @author Pawan Mahalle
 * 
 */

@Entity
@Table(name = "tbl_projectblogentry")
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "ProjectBlogEntryDTO.findAll", query = "SELECT pb FROM ProjectBlogEntryDTO pb"),
        @NamedQuery(name = "ProjectBlogEntryDTO.findByProjectId", query = "SELECT p FROM ProjectBlogEntryDTO p WHERE p.projectid = :projectId order by createdDate desc"), })
public class ProjectBlogEntryDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "projectblogentryid")
    private String projectBlogEntryId;

    @Column(name = "title")
    private String title;

    @Lob
    @Column(name = "description")
    private String description;
    
    @Column(name = "author")
    private String author;
    
    @Column(name = "createdDate")
    private Date createdDate;

    @Column(name = "projectid")
    private String projectid;
    
    @JoinColumn(name = "projectid", referencedColumnName = "projectid", insertable = false, updatable = false)
    @ManyToOne
    private ProjectDTO projectDTO;

    @JoinColumn(name = "author", referencedColumnName = "username", insertable = false, updatable = false)
    @ManyToOne
    private QuadrigaUserDTO projectBlogEntryAuthorDTO;

    /**
     * @return the projectBlogEntryId
     */
    public String getProjectBlogEntryId() {
        return projectBlogEntryId;
    }

    /**
     * @param projectBlogEntryId the projectBlogEntryId to set
     */
    public void setProjectBlogEntryId(String projectBlogEntryId) {
        this.projectBlogEntryId = projectBlogEntryId;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * @param author the author to set
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * @return the createdDate
     */
    public Date getCreatedDate() {
        return createdDate;
    }

    /**
     * @param createdDate the createdDate to set
     */
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * @return the projectid
     */
    public String getProjectid() {
        return projectid;
    }

    /**
     * @param projectid the projectid to set
     */
    public void setProjectid(String projectid) {
        this.projectid = projectid;
    }

    /**
     * @return the projectDTO
     */
    public ProjectDTO getProjectDTO() {
        return projectDTO;
    }

    /**
     * @param projectDTO the projectDTO to set
     */
    public void setProjectDTO(ProjectDTO projectDTO) {
        this.projectDTO = projectDTO;
    }

    /**
     * @return the projectBlogEntryAuthorDTO
     */
    public QuadrigaUserDTO getProjectBlogEntryAuthorDTO() {
        return projectBlogEntryAuthorDTO;
    }

    /**
     * @param projectBlogEntryAuthorDTO the projectBlogEntryAuthorDTO to set
     */
    public void setProjectBlogEntryAuthorDTO(QuadrigaUserDTO projectBlogEntryAuthorDTO) {
        this.projectBlogEntryAuthorDTO = projectBlogEntryAuthorDTO;
    }
}
