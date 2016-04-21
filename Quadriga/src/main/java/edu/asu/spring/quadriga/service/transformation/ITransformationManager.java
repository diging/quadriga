package edu.asu.spring.quadriga.service.transformation;

/**
 * 
 * @author JayaVenkat
 *
 */
public interface ITransformationManager {

	public void saveTransformation(String title, String description, String mappingTitle, String mappingDescription, String mappingFileName, String transfomrTitle, String transfomrDesc, String transformationFileName);
	
}
