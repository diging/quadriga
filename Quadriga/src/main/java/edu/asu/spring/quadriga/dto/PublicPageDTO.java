package edu.asu.spring.quadriga.dto;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_publicpage")
@NamedQueries({
        @NamedQuery(name = "PublicPageDTO.findAll", query = "SELECT pp FROM PublicPageDTO pp"),
        @NamedQuery(name = "PublicPageDTO.findByProjectId", query = "SELECT p FROM PublicPageDTO p WHERE p.projectid = :projectId"), })
public class PublicPageDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "publicpageid")
    private String publicpageid;

    @Column(name = "title")
    private String title;

    @Lob
    @Column(name = "description")
    private String description;

    @Column(name = "entryorder")
    private int entryorder;

    @Column(name = "projectid")
    private String projectid;

    @JoinColumn(name = "projectid", referencedColumnName = "projectid", insertable = false, updatable = false)
    @ManyToOne
    private ProjectDTO projectDTO;

    private String linkTo;
    private String linkText;

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

    public int getEntryorder() {
        return entryorder;
    }

    public void setEntryorder(int entryorder) {
        this.entryorder = entryorder;
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

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public String getPublicpageid() {
        return publicpageid;
    }

    public void setPublicpageid(String publicpageid) {
        this.publicpageid = publicpageid;
    }

    public String getLinkTo() {
        return linkTo;
    }

    public void setLinkTo(String linkTo) {
        this.linkTo = linkTo;
    }

    public String getLinkText() {
        return linkText;
    }

    public void setLinkText(String linkText) {
        this.linkText = linkText;
    }
}