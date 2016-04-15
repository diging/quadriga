package edu.asu.spring.quadriga.dto;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import javax.persistence.Id;

@Entity
@Table(name = "tbl_transfomationfiles_metadata")
@XmlRootElement
public class UploadTransfomationFilesDTO implements Serializable {

	
	private static final long serialVersionUID = 1L;

	public UploadTransfomationFilesDTO(String mappingTitle,
			String mappingDescription, String transfomationTitle,
			String transformationDescription) {
		super();
		this.mappingTitle = mappingTitle;
		this.mappingDescription = mappingDescription;
		this.transfomationTitle = transfomationTitle;
		this.transformationDescription = transformationDescription;
	}
	
	@Id
	private String Id;
	public String getId() {
		return Id;
	}
	
	public void setId(String id) {
		Id = id;
	}

	@Column(name = "Mapping_Title")
	private String  mappingTitle;
	@Column(name = "Mapping_Description")
	private String mappingDescription;
	/*private File mappingFile;*/
	
	@Column(name = "Transform_Title")
	private String transfomationTitle;
	@Column(name = "TransformDescription")
	private String transformationDescription;
	/*private File tranformationFile;*/
	
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
	public String getTransfomationTitle() {
		return transfomationTitle;
	}
	public void setTransfomationTitle(String transfomationTitle) {
		this.transfomationTitle = transfomationTitle;
	}
	public String getTransformationDescription() {
		return transformationDescription;
	}
	public void setTransformationDescription(String transformationDescription) {
		this.transformationDescription = transformationDescription;
	}

}
