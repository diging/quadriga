package edu.asu.spring.quadriga.dto;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "tbl_external_workspace")
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "ExternalWorkspaceDTO.findAll", query = "SELECT w FROM ExternalWorkspaceDTO w"),
        @NamedQuery(name = "ExternalWorkspaceDTO.findByWorkspaceid", query = "SELECT w FROM ExternalWorkspaceDTO w WHERE w.externalWorkspaceid = :externalWorkspaceid"),
        @NamedQuery(name = "ExternalWorkspaceDTO.findByExternalWorkspaceid", query = "SELECT w FROM ExternalWorkspaceDTO w WHERE w.externalWorkspaceid = :externalWorkspaceid"),
         })
public class ExternalWorkspaceDTO {

    @Id
    @Basic(optional = false)
    @Column(name = "externalWorkspaceid")
    private String externalWorkspaceid;

    @JoinColumn(name = "workspaceid", referencedColumnName = "workspaceid", insertable = false, updatable = false)
    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    private WorkspaceDTO workspaceDTO;

    public String getExternalWorkspaceid() {
        return externalWorkspaceid;
    }

    public void setExternalWorkspaceid(String externalWorkspaceid) {
        this.externalWorkspaceid = externalWorkspaceid;
    }

    public WorkspaceDTO getWorkspaceDTO() {
        return workspaceDTO;
    }

    public void setWorkspaceDTO(WorkspaceDTO workspaceDTO) {
        this.workspaceDTO = workspaceDTO;
    }

}
