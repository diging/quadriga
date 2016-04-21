package edu.asu.spring.quadriga.service.impl.transformation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.service.transformation.ITransformationManager;
import edu.asu.spring.quadriga.dao.impl.transform.TransformFilesDAO;
import edu.asu.spring.quadriga.dto.TransformFilesDTO;
import org.springframework.transaction.annotation.Transactional;

/**
 * This class is a service which takes the transformation files metadata and
 * creates a DTO and saves this DTO in database using DAO
 * 
 * @author JayaVenkat
 *
 */
@Service
public class TransformationManager implements ITransformationManager {

	@Autowired
	private TransformFilesDAO uploadTransformationDAO;

	@Transactional
	@Override
	public void saveTransformation(String title, String description, String mappingTitle, String mappingDescription,
			String mappingFileName, String transformationTitle,
			String transformationDescription, String transformationFileName) {
		TransformFilesDTO tranformDTO = new TransformFilesDTO(
				title, description, mappingTitle, mappingDescription, mappingFileName,
				transformationTitle, transformationDescription,
				transformationFileName);
		tranformDTO.setId(uploadTransformationDAO.generateUniqueID());
		uploadTransformationDAO.saveTransformDTO(tranformDTO);

	}

}
