/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.asu.spring.quadriga.dto;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *This class represents the column mappings for project dictionary table.
 * @author Karthik
 */
@Entity
@Table(name = "tbl_project_dictionary")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProjectDictionaryDTO.findAll", query = "SELECT p FROM ProjectDictionaryDTO p"),
    @NamedQuery(name = "ProjectDictionaryDTO.findByProjectid", query = "SELECT p FROM ProjectDictionaryDTO p WHERE p.projectDictionaryDTOPK.projectid = :projectid"),
    @NamedQuery(name = "ProjectDictionaryDTO.findByDictionaryid", query = "SELECT p FROM ProjectDictionaryDTO p WHERE p.projectDictionaryDTOPK.dictionaryid = :dictionaryid"),
    })

public class ProjectDictionaryDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @EmbeddedId
    protected ProjectDictionaryDTOPK projectDictionaryDTOPK;
    @JoinColumn(name = "projectid", referencedColumnName = "projectid", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private ProjectDTO project;
    @JoinColumn(name = "dictionaryid" , referencedColumnName = "dictionaryid",insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private DictionaryDTO dictionary;
    
	@Basic(optional = false)
    @Column(name = "updatedby")
    private String updatedby;
    @Basic(optional = false)
    @Column(name = "updateddate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateddate;
    @Basic(optional = false)
    @Column(name = "createdby")
    private String createdby;
    @Basic(optional = false)
    @Column(name = "createddate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createddate;

    public ProjectDictionaryDTO() {
    }

    public ProjectDictionaryDTO(ProjectDictionaryDTOPK projectDictionaryDTOPK, String updatedby, Date updateddate, String createdby, Date createddate) {
        this.projectDictionaryDTOPK = projectDictionaryDTOPK;
        this.updatedby = updatedby;
        this.updateddate = updateddate;
        this.createdby = createdby;
        this.createddate = createddate;
    }

    public ProjectDictionaryDTO(String projectid, String dictionaryid,String updatedby, Date updateddate, String createdby, Date createddate) {
        this.projectDictionaryDTOPK = new ProjectDictionaryDTOPK(projectid, dictionaryid);
        this.updatedby = updatedby;
        this.updateddate = updateddate;
        this.createdby = createdby;
        this.createddate = createddate;
    }

    public ProjectDictionaryDTOPK getProjectDictionaryDTOPK() {
        return projectDictionaryDTOPK;
    }

    public void setProjectDictionaryDTOPK(ProjectDictionaryDTOPK projectDictionaryDTOPK) {
        this.projectDictionaryDTOPK = projectDictionaryDTOPK;
    }
    
    public ProjectDTO getProject() {
		return project;
	}

	public void setProject(ProjectDTO project) {
		this.project = project;
	}

	public DictionaryDTO getDictionary() {
		return dictionary;
	}

	public void setDictionary(DictionaryDTO dictionary) {
		this.dictionary = dictionary;
	}

    public String getUpdatedby() {
        return updatedby;
    }

    public void setUpdatedby(String updatedby) {
        this.updatedby = updatedby;
    }

    public Date getUpdateddate() {
        return updateddate;
    }

    public void setUpdateddate(Date updateddate) {
        this.updateddate = updateddate;
    }

    public String getCreatedby() {
        return createdby;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }

    public Date getCreateddate() {
        return createddate;
    }

    public void setCreateddate(Date createddate) {
        this.createddate = createddate;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (projectDictionaryDTOPK != null ? projectDictionaryDTOPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof ProjectDictionaryDTO)) {
            return false;
        }
        ProjectDictionaryDTO other = (ProjectDictionaryDTO) object;
        if ((this.projectDictionaryDTOPK == null && other.projectDictionaryDTOPK != null) || (this.projectDictionaryDTOPK != null && !this.projectDictionaryDTOPK.equals(other.projectDictionaryDTOPK))) {
            return false;
        }
        return true;
    }
}
