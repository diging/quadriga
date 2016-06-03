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
 *This class represents the column mappings for project concept collection table.
 * @author Karthik
 */
@Entity
@Table(name = "tbl_project_conceptcollection")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProjectConceptCollectionDTO.findAll", query = "SELECT p FROM ProjectConceptCollectionDTO p"),
    @NamedQuery(name = "ProjectConceptCollectionDTO.findByProjectid", query = "SELECT p FROM ProjectConceptCollectionDTO p WHERE p.projectConceptcollectionDTOPK.projectid = :projectid"),
    @NamedQuery(name = "ProjectConceptCollectionDTO.findByConceptcollectionid", query = "SELECT p FROM ProjectConceptCollectionDTO p WHERE p.projectConceptcollectionDTOPK.conceptcollectionid = :conceptcollectionid"),
    })
public class ProjectConceptCollectionDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ProjectConceptCollectionDTOPK projectConceptcollectionDTOPK;
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
    @JoinColumn(name = "projectid", referencedColumnName = "projectid", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private ProjectDTO projectDTO;
    @JoinColumn(name = "conceptcollectionid", referencedColumnName = "conceptcollectionid", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private ConceptCollectionDTO conceptCollection;

	public ProjectConceptCollectionDTO() {
    }

    public ProjectConceptCollectionDTO(ProjectConceptCollectionDTOPK projectConceptcollectionDTOPK, String updatedby, Date updateddate, String createdby, Date createddate) {
        this.projectConceptcollectionDTOPK = projectConceptcollectionDTOPK;
        this.updatedby = updatedby;
        this.updateddate = updateddate;
        this.createdby = createdby;
        this.createddate = createddate;
    }

    public ProjectConceptCollectionDTO(String projectid, String conceptcollectionid) {
        this.projectConceptcollectionDTOPK = new ProjectConceptCollectionDTOPK(projectid, conceptcollectionid);
    }

    public ProjectConceptCollectionDTOPK getProjectConceptcollectionDTOPK() {
        return projectConceptcollectionDTOPK;
    }

    public void setProjectConceptcollectionDTOPK(ProjectConceptCollectionDTOPK projectConceptcollectionDTOPK) {
        this.projectConceptcollectionDTOPK = projectConceptcollectionDTOPK;
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
    
    public ProjectDTO getProjectDTO() {
		return projectDTO;
	}

	public void setProjectDTO(ProjectDTO projectDTO) {
		this.projectDTO = projectDTO;
	}

	public ConceptCollectionDTO getConceptCollection() {
		return conceptCollection;
	}

	public void setConceptCollection(ConceptCollectionDTO conceptCollection) {
		this.conceptCollection = conceptCollection;
	}

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (projectConceptcollectionDTOPK != null ? projectConceptcollectionDTOPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof ProjectConceptCollectionDTO)) {
            return false;
        }
        ProjectConceptCollectionDTO other = (ProjectConceptCollectionDTO) object;
        if ((this.projectConceptcollectionDTOPK == null && other.projectConceptcollectionDTOPK != null) || (this.projectConceptcollectionDTOPK != null && !this.projectConceptcollectionDTOPK.equals(other.projectConceptcollectionDTOPK))) {
            return false;
        }
        return true;
    }
}
