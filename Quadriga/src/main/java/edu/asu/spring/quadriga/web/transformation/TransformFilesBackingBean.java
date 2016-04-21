package edu.asu.spring.quadriga.web.transformation;

/**
 * BackingBean class which is used to store the data in the form to upload
 * transformation files
 * 
 * @author JayaVenkat
 *
 */
public class TransformFilesBackingBean {

	private String title;
    private String description;
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

	private String mappingTitle;
	private String mappingDescription;
	private String transformTitle;
	private String transformDescription;
    
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

	public String getTransformTitle() {
		return transformTitle;
	}

	public void setTransformTitle(String transformTitle) {
		this.transformTitle = transformTitle;
	}

	public String getTransformDescription() {
		return transformDescription;
	}

	public void setTransformDescription(String transformDescription) {
		this.transformDescription = transformDescription;
	}

}
