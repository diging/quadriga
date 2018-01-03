package edu.asu.spring.quadriga.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * This class represents the column mappings for project table.
 * 
 * @author Karthik
 */
@Entity
@Table(name = "tbl_project")
@Inheritance(strategy = InheritanceType.JOINED)

@NamedQueries({ @NamedQuery(name = "ProjectDTO.findAll", query = "SELECT p FROM ProjectDTO p"),
        @NamedQuery(name = "ProjectDTO.findByProjectname", query = "SELECT p FROM ProjectDTO p WHERE p.projectname = :projectname"),
        @NamedQuery(name = "ProjectDTO.findByUnixname", query = "SELECT p FROM ProjectDTO p WHERE p.unixname = :unixname"),
        @NamedQuery(name = "ProjectDTO.findByProjectid", query = "SELECT p FROM ProjectDTO p WHERE p.projectid = :projectid"),
        @NamedQuery(name = "ProjectDTO.findByAccessibility", query = "SELECT p FROM ProjectDTO p WHERE p.accessibility = :accessibility"), })

public class ProjectDTO extends CollaboratingDTO<ProjectCollaboratorDTOPK, ProjectCollaboratorDTO>
        implements Serializable {
    private static final long serialVersionUID = 1L;

    @Basic(optional = false)
    @Column(name = "projectname")
    private String projectname;
    @Lob
    @Column(name = "description")
    private String description;
    @Basic(optional = false)
    @Column(name = "unixname")
    private String unixname;
    @Id
    @Basic(optional = false)
    @Column(name = "projectid")
    private String projectid;
    @Basic(optional = false)
    @Column(name = "accessibility")
    private String accessibility;

    @JoinColumn(name = "projectowner", referencedColumnName = "username")
    @ManyToOne(optional = false)
    private QuadrigaUserDTO projectowner;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "projectDTO", orphanRemoval = true)
    private List<ProjectWorkspaceDTO> projectWorkspaceDTOList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "project", orphanRemoval = true)
    private List<ProjectDictionaryDTO> projectDictionaryDTOList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "projectDTO", orphanRemoval = true)
    private List<ProjectConceptCollectionDTO> projectConceptCollectionDTOList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "projectDTO", orphanRemoval = true)
    private List<ProjectCollaboratorDTO> projectCollaboratorDTOList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "project", orphanRemoval = true)
    private List<ProjectEditorDTO> projectEditorDTOList;

    @ManyToMany
    @JoinTable(name = "tbl_project_resolvers")
    private List<ProjectHandleResolverDTO> resolvers;

    public List<ProjectHandleResolverDTO> getResolvers() {
        return resolvers;
    }

    public void setResolvers(List<ProjectHandleResolverDTO> resolver) {
        this.resolvers = resolver;
    }

    public ProjectDTO() {
    }

    public ProjectDTO(String projectid, String projectname, String unixname, String accessibility, String updatedby,
            Date updateddate, String createdby, Date createddate) {
        this.projectid = projectid;
        this.projectname = projectname;
        this.unixname = unixname;
        this.accessibility = accessibility;
        this.updatedby = updatedby;
        this.updateddate = updateddate;
        this.createdby = createdby;
        this.createddate = createddate;
    }

    public String getProjectname() {
        return projectname;
    }

    public void setProjectname(String projectname) {
        this.projectname = projectname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUnixname() {
        return unixname;
    }

    public void setUnixname(String unixname) {
        this.unixname = unixname;
    }

    public String getProjectid() {
        return projectid;
    }

    public void setProjectid(String projectid) {
        this.projectid = projectid;
    }

    public String getAccessibility() {
        return accessibility;
    }

    public void setAccessibility(String accessibility) {
        this.accessibility = accessibility;
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

    public QuadrigaUserDTO getProjectowner() {
        return projectowner;
    }

    public void setProjectowner(QuadrigaUserDTO projectowner) {
        this.projectowner = projectowner;
    }

    public List<ProjectDictionaryDTO> getProjectDictionaryDTOList() {
        return projectDictionaryDTOList;
    }

    public void setProjectDictionaryDTOList(List<ProjectDictionaryDTO> projectDictionaryDTOList) {
        this.projectDictionaryDTOList = projectDictionaryDTOList;
    }

    public List<ProjectConceptCollectionDTO> getProjectConceptCollectionDTOList() {
        return projectConceptCollectionDTOList;
    }

    public void setProjectConceptCollectionDTOList(List<ProjectConceptCollectionDTO> projectConceptCollectionDTOList) {
        this.projectConceptCollectionDTOList = projectConceptCollectionDTOList;
    }

    public List<ProjectEditorDTO> getProjectEditorDTOList() {
        return projectEditorDTOList;
    }

    public void setProjectEditorDTOList(List<ProjectEditorDTO> projectEditorDTOList) {
        this.projectEditorDTOList = projectEditorDTOList;
    }

    public List<ProjectWorkspaceDTO> getProjectWorkspaceDTOList() {
        return projectWorkspaceDTOList;
    }

    public void setProjectWorkspaceDTOList(List<ProjectWorkspaceDTO> projectWorkspaceDTOList) {
        this.projectWorkspaceDTOList = projectWorkspaceDTOList;
    }

    public List<ProjectCollaboratorDTO> getProjectCollaboratorDTOList() {
        return projectCollaboratorDTOList;
    }

    public void setProjectCollaboratorDTOList(List<ProjectCollaboratorDTO> projectCollaboratorDTOList) {
        this.projectCollaboratorDTOList = projectCollaboratorDTOList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (projectid != null ? projectid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof ProjectDTO)) {
            return false;
        }
        ProjectDTO other = (ProjectDTO) object;
        if ((this.projectid == null && other.projectid != null)
                || (this.projectid != null && !this.projectid.equals(other.projectid))) {
            return false;
        }
        return true;
    }

    @Override
    public List<ProjectCollaboratorDTO> getCollaboratorList() {
        return projectCollaboratorDTOList;
    }

    @Override
    public void setCollaboratorList(List<ProjectCollaboratorDTO> list) {
        projectCollaboratorDTOList = list;
    }

    @Override
    public String getId() {
        return projectid;
    }

    @Override
    public QuadrigaUserDTO getOwner() {
        return projectowner;
    }

    @Override
    public void setOwner(QuadrigaUserDTO owner) {
        this.projectowner = owner;
    }
}