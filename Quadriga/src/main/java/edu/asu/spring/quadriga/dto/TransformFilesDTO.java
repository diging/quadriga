package edu.asu.spring.quadriga.dto;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * DTO for holding the metadata of transformation files
 * 
 * @author JayaVenkat
 *
 */
@Entity
@Table(name = "tbl_transfomationfiles_metadata")
public class TransformFilesDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private String id;
    @Column(name = "Title")
    private String title;
    @Column(name = "Description")
    private String description;
    @Column(name = "Pattern_FileName")
    private String patternFileName;
    @Column(name = "Pattern_Title")
    private String patternTitle;
    @Column(name = "Pattern_Description")
    private String patternDescription;
    @Column(name = "Mapping_FileName")
    private String mappingFileName;
    @Column(name = "Mapping_Title")
    private String mappingTitle;
    @Column(name = "Mapping_Description")
    private String mappingDescription;
    @Column(name = "User_Name")
    private String userName;

    public TransformFilesDTO() {
        super();
    }

    public TransformFilesDTO(String title, String description, String patternFileName, String patternTitle,
            String patternDescription, String mappingFileName, String mappingTitle, String mappingDescription,
            String userName) {
        super();
        this.title = title;
        this.description = description;
        this.patternTitle = patternTitle;
        this.patternDescription = patternDescription;
        this.patternFileName = patternFileName;
        this.mappingTitle = mappingTitle;
        this.mappingDescription = mappingDescription;
        this.mappingFileName = mappingFileName;
        this.userName = userName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMappingTitle() {
        return mappingTitle;
    }

    public void setMappingTitle(String mappingTitle) {
        this.mappingTitle = mappingTitle;
    }

    public String getMappingDescription() {
        return mappingDescription;
    }

    public void setMappingDescription(String mappingDescription) {
        this.mappingDescription = mappingDescription;
    }

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

    public String getPatternTitle() {
        return patternTitle;
    }

    public void setPatternTitle(String patternTitle) {
        this.patternTitle = patternTitle;
    }

    public String getPatternDescription() {
        return patternDescription;
    }

    public void setPatternDescription(String patternDescription) {
        this.patternDescription = patternDescription;
    }

    public String getPatternFileName() {
        return patternFileName;
    }

    public void setPatternFileName(String patternFileName) {
        this.patternFileName = patternFileName;
    }

    public String getMappingFileName() {
        return mappingFileName;
    }

    public void setMappingFileName(String mappingFileName) {
        this.mappingFileName = mappingFileName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }
}
