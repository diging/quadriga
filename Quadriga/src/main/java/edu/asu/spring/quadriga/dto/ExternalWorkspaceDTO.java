package edu.asu.spring.quadriga.dto;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "tbl_external_workspace")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "ExternalWorkspaceDTO.findAll", query = "SELECT w FROM ExternalWorkspaceDTO w"),
        @NamedQuery(name = "ExternalWorkspaceDTO.findByWorkspaceid", query = "SELECT w FROM ExternalWorkspaceDTO w WHERE w.externalWorkspaceid = :externalWorkspaceid"),
        @NamedQuery(name = "ExternalWorkspaceDTO.findByExternalWorkspaceid", query = "SELECT w FROM ExternalWorkspaceDTO w WHERE w.externalWorkspaceid = :externalWorkspaceid"), 
        @NamedQuery(name = "ExternalWorkspaceDTO.getWorkspaceIdFromExternalWorkspaceId", query = "SELECT w.workspaceid FROM WorkspaceDTO w INNER JOIN  w.externalWorkspaceDTO e where e.internalWorkspaceid = w.workspaceid AND e.externalWorkspaceid = :externalWorkspaceid "),})
public class ExternalWorkspaceDTO extends WorkspaceDTO {

    private static final long serialVersionUID = 1L;

    @Basic(optional = false)
    @Column(name = "externalWorkspaceid")
    private String externalWorkspaceid;
    
    @Basic(optional = false)
    @Column(name = "internalWorkspaceid")
    private String internalWorkspaceid;
    
    public String getExternalWorkspaceid() {
        return externalWorkspaceid;
    }

    public String getInternalWorkspaceid() {
        return internalWorkspaceid;
    }

    public void setInternalWorkspaceid(String internalWorkspaceid) {
        this.internalWorkspaceid = internalWorkspaceid;
    }

    public void setExternalWorkspaceid(String externalWorkspaceid) {
        this.externalWorkspaceid = externalWorkspaceid;
    }

}
