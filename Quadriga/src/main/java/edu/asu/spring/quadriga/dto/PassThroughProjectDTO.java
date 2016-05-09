package edu.asu.spring.quadriga.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * This class represents the column mappings for external project table.
 * 
 */
@Entity
@Table(name = "tbl_external_project")
@NamedQueries({ @NamedQuery(name = "PassThroughProjectDTO.findAll", query = "SELECT p FROM PassThroughProjectDTO p"),
        @NamedQuery(name = "PassThroughProjectDTO.findByProjectid", query = "SELECT p FROM PassThroughProjectDTO p WHERE p.projectid = :projectid"),
        @NamedQuery(name = "PassThroughProjectDTO.findByExternalProjectid", query = "SELECT p FROM PassThroughProjectDTO p WHERE p.externalProjectid = :externalProjectid"), })

public class PassThroughProjectDTO extends ProjectDTO {
    private static final long serialVersionUID = 1L;

    @Column(name = "externaluserid")
    private String externalUserId;

    @Column(name = "externalusername")
    private String externalUserName;

    @Column(name = "client")
    private String client;

    @Column(name = "externalprojectid")
    private String externalProjectid;

    public PassThroughProjectDTO() {
    }

    public String getExternalUserId() {
        return externalUserId;
    }

    public void setExternalUserId(String externalUserId) {
        this.externalUserId = externalUserId;
    }

    public String getExternalUserName() {
        return externalUserName;
    }

    public void setExternalUserName(String externalUserName) {
        this.externalUserName = externalUserName;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getExternalProjectid() {
        return externalProjectid;
    }

    public void setExternalProjectid(String externalProjectid) {
        this.externalProjectid = externalProjectid;
    }

}
