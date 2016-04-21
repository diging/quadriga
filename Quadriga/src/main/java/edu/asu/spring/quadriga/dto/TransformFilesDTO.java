package edu.asu.spring.quadriga.dto;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;

import javax.persistence.Id;

/**
 * DTO for holding the metadata of transformation files
 * 
 * @author JayaVenkat
 *
 */
@Entity
@Table(name = "tbl_transfomationfiles_metadata")
@XmlRootElement
public class TransformFilesDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	private String Id;
	@Column(name="Title")
	String title;
	@Column(name="Description")
	String description;
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

	public TransformFilesDTO() {
		super();
	}

	public TransformFilesDTO(String title, String description,
			 String patternFileName, String patternTitle, String patternDescription,
			 String mappingFileName,String mappingTitle, String mappingDescription) {
		super();
		this.title = title;
		this.description = description;
		this.patternTitle = patternTitle;
		this.patternDescription = patternDescription;
		this.patternFileName = patternFileName;
		this.mappingTitle = mappingTitle;
		this.mappingDescription = mappingDescription;
		this.mappingFileName = mappingFileName;
	}

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
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

	public void setPatternFile(String patternFileName) {
		this.patternFileName = patternFileName;
	}

	public String getMappingFileName() {
		return mappingFileName;
	}

	public void setMappingFile(String mappingFileName) {
		this.mappingFileName = mappingFileName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}


}