package edu.asu.spring.quadriga.web.uploadtransformation;

import java.io.File;

public class UploadTransformationBackingBean {
	
	private String  mappingTitle;
	private String mappingDescription;
	/*private File mappingFile;*/
	
	private String transformTitle;
	private String transformDescription;
	/*private File tranformFile;*/
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
		return transformTitle;
	}
	public void setTransfomationTitle(String transfomationTitle) {
		this.transformTitle = transfomationTitle;
	}
	public String getTransformationDescription() {
		return transformDescription;
	}
	public void setTransformationDescription(String transformationDescription) {
		this.transformDescription = transformationDescription;
	}

}
