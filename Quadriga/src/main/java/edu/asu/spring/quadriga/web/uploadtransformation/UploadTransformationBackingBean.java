package edu.asu.spring.quadriga.web.uploadtransformation;

import java.io.File;

public class UploadTransformationBackingBean {
	
	private String  mappingFileTitle;
	private String mappingFileDescription;
	private File mappingFile;
	
	private String transfomationFileTitle;
	private String transformationFileDescription;
	private File tranformationFile;
	public String getMappingFileTitle() {
		return mappingFileTitle;
	}
	public void setMappingFileTitle(String mappingFileTitle) {
		this.mappingFileTitle = mappingFileTitle;
	}
	public String getMappingFileDescription() {
		return mappingFileDescription;
	}
	public void setMappingFileDescription(String mappingFileDescription) {
		this.mappingFileDescription = mappingFileDescription;
	}
	public File getMappingFile() {
		return mappingFile;
	}
	public void setMappingFile(File mappingFile) {
		this.mappingFile = mappingFile;
	}
	public String getTransfomationFileTitle() {
		return transfomationFileTitle;
	}
	public void setTransfomationFileTitle(String transfomationFileTitle) {
		this.transfomationFileTitle = transfomationFileTitle;
	}
	public String getTransformationFileDescription() {
		return transformationFileDescription;
	}
	public void setTransformationFileDescription(
			String transformationFileDescription) {
		this.transformationFileDescription = transformationFileDescription;
	}
	public File getTranformationFile() {
		return tranformationFile;
	}
	public void setTranformationFile(File tranformationFile) {
		this.tranformationFile = tranformationFile;
	}
	
	

}
