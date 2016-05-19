/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.asu.spring.quadriga.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *This table represents the column mappings for quadriga user table.
 * @author Karthik
 */
@Entity
@Table(name = "tbl_quadriga_user")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "QuadrigaUserDTO.findAll", query = "SELECT q FROM QuadrigaUserDTO q"),
    @NamedQuery(name = "QuadrigaUserDTO.findByFullname", query = "SELECT q FROM QuadrigaUserDTO q WHERE q.fullname = :fullname"),
    @NamedQuery(name = "QuadrigaUserDTO.findByUsername", query = "SELECT q FROM QuadrigaUserDTO q WHERE q.username = :username"),
    @NamedQuery(name = "QuadrigaUserDTO.findByEmail", query = "SELECT q FROM QuadrigaUserDTO q WHERE q.email = :email"),
    @NamedQuery(name = "QuadrigaUserDTO.findByQuadrigarole", query = "SELECT q FROM QuadrigaUserDTO q WHERE q.quadrigarole = :quadrigarole"),
    })
public class QuadrigaUserDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name = "fullname")
    private String fullname;
    @Id
    @Basic(optional = false)
    @Column(name = "username")
    private String username;
    @Column(name = "passwd")
    private String passwd;
    @Column(name = "email")
    private String email;
    @Basic(optional = false)
    @Column(name = "quadrigarole")
    private String quadrigarole;
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
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "quadrigaUserDTO")
    private List<ConceptCollectionCollaboratorDTO> conceptcollectionsCollaboratorDTOList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "projectowner")
    private List<ProjectDTO> projectDTOList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "workspaceowner")
    private List<WorkspaceDTO> workspaceDTOList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "quadrigaUserDTO")
    private List<WorkspaceCollaboratorDTO> workspaceCollaboratorDTOList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "quadrigaUserDTO")
    private List<ProjectCollaboratorDTO> projectCollaboratorDTOList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "collectionowner")
    private List<ConceptCollectionDTO> conceptcollectionsDTOList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "quadrigaUserDTO")
    private List<DictionaryCollaboratorDTO> dictionaryCollaboratorDTOList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "dictionaryowner")
    private List<DictionaryDTO> dictionaryDTOList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "quadrigaUserDTO")
    private List<NetworksDTO> networkDTOList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "quadrigaUserDTO")
    private List<NetworkAssignedDTO> networkAssignedList; 
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "quadrigaUserDTO")
    private List<NetworkAnnotationsDTO> networkAnnotaionList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "quadrigaUserDTO")
    private List<QuadrigaUserprofileDTO> quadrigaUserprofileDTOList;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "quadrigaUserDTO")
    private List<QuadrigaUserDeniedDTO> quadrigaUserDeniedDTOList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "quadrigaUserDTO")
    private List<QuadrigaUserRoleDTO> quadrigaUserRoleDTOList;

    public QuadrigaUserDTO() {
    }

	public QuadrigaUserDTO(String username, String quadrigarole, String updatedby, Date updateddate, String createdby, Date createddate) {
        this.username = username;
        this.quadrigarole = quadrigarole;
        this.updatedby = updatedby;
        this.updateddate = updateddate;
        this.createdby = createdby;
        this.createddate = createddate;
    }
	
	   @XmlTransient
	   public List<NetworkAnnotationsDTO> getNetworkAnnotaionList() {
			return networkAnnotaionList;
		}

		public void setNetworkAnnotaionList(
				List<NetworkAnnotationsDTO> networkAnnotaionList) {
			this.networkAnnotaionList = networkAnnotaionList;
		}

		@XmlTransient
		public List<QuadrigaUserprofileDTO> getQuadrigaUserprofileDTOList() {
			return quadrigaUserprofileDTOList;
		}

		public void setQuadrigaUserprofileDTOList(
				List<QuadrigaUserprofileDTO> quadrigaUserprofileDTOList) {
			this.quadrigaUserprofileDTOList = quadrigaUserprofileDTOList;
		}

		@XmlTransient
		public List<QuadrigaUserDeniedDTO> getQuadrigaUserDeniedDTOList() {
			return quadrigaUserDeniedDTOList;
		}

		public void setQuadrigaUserDeniedDTOList(
				List<QuadrigaUserDeniedDTO> quadrigaUserDeniedDTOList) {
			this.quadrigaUserDeniedDTOList = quadrigaUserDeniedDTOList;
		}

		@XmlTransient
		public List<QuadrigaUserRoleDTO> getQuadrigaUserRoleDTOList() {
			return quadrigaUserRoleDTOList;
		}

		public void setQuadrigaUserRoleDTOList(
				List<QuadrigaUserRoleDTO> quadrigaUserRoleDTOList) {
			this.quadrigaUserRoleDTOList = quadrigaUserRoleDTOList;
		}

		@XmlTransient
    public List<NetworkAssignedDTO> getNetworkAssignedList() {
		return networkAssignedList;
	}

	public void setNetworkAssignedList(List<NetworkAssignedDTO> networkAssignedList) {
		this.networkAssignedList = networkAssignedList;
	}

	@XmlTransient
    public List<NetworksDTO> getNetworkDTOList() {
		return networkDTOList;
	}

	public void setNetworkDTOList(List<NetworksDTO> networkDTOList) {
		this.networkDTOList = networkDTOList;
	}

	public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getQuadrigarole() {
        return quadrigarole;
    }

    public void setQuadrigarole(String quadrigarole) {
        this.quadrigarole = quadrigarole;
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

    @XmlTransient
    public List<ConceptCollectionCollaboratorDTO> getConceptcollectionsCollaboratorDTOList() {
        return conceptcollectionsCollaboratorDTOList;
    }

    public void setConceptcollectionsCollaboratorDTOList(List<ConceptCollectionCollaboratorDTO> conceptcollectionsCollaboratorDTOList) {
        this.conceptcollectionsCollaboratorDTOList = conceptcollectionsCollaboratorDTOList;
    }

    @XmlTransient
    public List<ProjectDTO> getProjectDTOList() {
        return projectDTOList;
    }

    public void setProjectDTOList(List<ProjectDTO> projectDTOList) {
        this.projectDTOList = projectDTOList;
    }

    @XmlTransient
    public List<WorkspaceDTO> getWorkspaceDTOList() {
        return workspaceDTOList;
    }

    public void setWorkspaceDTOList(List<WorkspaceDTO> workspaceDTOList) {
        this.workspaceDTOList = workspaceDTOList;
    }

    @XmlTransient
    public List<WorkspaceCollaboratorDTO> getWorkspaceCollaboratorDTOList() {
        return workspaceCollaboratorDTOList;
    }

    public void setWorkspaceCollaboratorDTOList(List<WorkspaceCollaboratorDTO> workspaceCollaboratorDTOList) {
        this.workspaceCollaboratorDTOList = workspaceCollaboratorDTOList;
    }

    @XmlTransient
    public List<ProjectCollaboratorDTO> getProjectCollaboratorDTOList() {
        return projectCollaboratorDTOList;
    }

    public void setProjectCollaboratorDTOList(List<ProjectCollaboratorDTO> projectCollaboratorDTOList) {
        this.projectCollaboratorDTOList = projectCollaboratorDTOList;
    }

    @XmlTransient
    public List<ConceptCollectionDTO> getConceptcollectionsDTOList() {
        return conceptcollectionsDTOList;
    }

    public void setConceptcollectionsDTOList(List<ConceptCollectionDTO> conceptcollectionsDTOList) {
        this.conceptcollectionsDTOList = conceptcollectionsDTOList;
    }

    @XmlTransient
    public List<DictionaryCollaboratorDTO> getDictionaryCollaboratorDTOList() {
        return dictionaryCollaboratorDTOList;
    }

    public void setDictionaryCollaboratorDTOList(List<DictionaryCollaboratorDTO> dictionaryCollaboratorDTOList) {
        this.dictionaryCollaboratorDTOList = dictionaryCollaboratorDTOList;
    }

    @XmlTransient
    public List<DictionaryDTO> getDictionaryDTOList() {
        return dictionaryDTOList;
    }

    public void setDictionaryDTOList(List<DictionaryDTO> dictionaryDTOList) {
        this.dictionaryDTOList = dictionaryDTOList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (username != null ? username.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof QuadrigaUserDTO)) {
            return false;
        }
        QuadrigaUserDTO other = (QuadrigaUserDTO) object;
        if ((this.username == null && other.username != null) || (this.username != null && !this.username.equals(other.username))) {
            return false;
        }
        return true;
    }
}
