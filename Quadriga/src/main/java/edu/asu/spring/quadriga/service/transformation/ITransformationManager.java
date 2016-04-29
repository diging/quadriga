package edu.asu.spring.quadriga.service.transformation;

import java.util.List;

import edu.asu.spring.quadriga.dto.TransformFilesDTO;

/**
 * 
 * @author JayaVenkat
 *
 */
public interface ITransformationManager {

    public void saveTransformation(String title, String description,
            String mappingTitle, String mappingDescription,
            String mappingFileName, String transfomrTitle,
            String transfomrDesc, String transformationFileName,String userName);
    public List<TransformFilesDTO> getTransformationsList();

}
