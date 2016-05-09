package edu.asu.spring.quadriga.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 * This class represents the column mappings for the workspace table.
 * 
 */
@Entity
@NamedQueries({ @NamedQuery(name = "ExternalWorkspaceDTO.findAll", query = "SELECT w FROM ExternalWorkspaceDTO w"),
        @NamedQuery(name = "ExternalWorkspaceDTO.findByWorkspaceid", query = "SELECT w FROM ExternalWorkspaceDTO w WHERE w.externalWorkspaceid = :externalWorkspaceid"),
        @NamedQuery(name = "ExternalWorkspaceDTO.findByExternalWorkspaceid", query = "SELECT w FROM ExternalWorkspaceDTO w WHERE w.externalWorkspaceid = :externalWorkspaceid"),
        @NamedQuery(name = "ExternalWorkspaceDTO.getWorkspaceIdFromExternalWorkspaceId", query = "SELECT w.workspaceid FROM ExternalWorkspaceDTO w WHERE w.externalWorkspaceid = :externalWorkspaceid"), })
public class ExternalWorkspaceDTO extends WorkspaceDTO {

    private static final long serialVersionUID = 1L;

    @Column(name = "externalWorkspaceid")
    private String externalWorkspaceid;

    public String getExternalWorkspaceid() {
        return externalWorkspaceid;
    }

    public void setExternalWorkspaceid(String externalWorkspaceid) {
        this.externalWorkspaceid = externalWorkspaceid;
    }
}
