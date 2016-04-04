package edu.asu.spring.quadriga.web.uploadtransformation;

import java.io.File;

public class UploadTransformationBackingBean {
	
	private String  mappingTitle;
	private String mappingDescription;
	/*private File mappingFile;*/
	
	private String transfomationTitle;
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
