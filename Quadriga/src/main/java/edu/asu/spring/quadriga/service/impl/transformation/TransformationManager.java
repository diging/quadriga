package edu.asu.spring.quadriga.service.impl.transformation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.service.transformation.ITransformationManager;
import edu.asu.spring.quadriga.dao.impl.transform.TransformFilesDAO;
import edu.asu.spring.quadriga.dto.TransformFilesDTO;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

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
	private TransformFilesDAO transformationDAO;

	@Transactional
	@Override
	public void saveTransformation(String title, String description,
			String patternFileName, String patternTitle, String patternDescription,
			 String mappingFileName,String mappingTitle,
			String mappingDescription) {
		TransformFilesDTO tranformDTO = new TransformFilesDTO(title,
				description, patternFileName, patternTitle, patternDescription, 
				mappingFileName, mappingTitle, mappingDescription);
		tranformDTO.setId(transformationDAO.generateUniqueID());
		transformationDAO.saveTransformDTO(tranformDTO);

	}
	
	@Transactional
	@Override
	public List<TransformFilesDTO> getTransformationsList(){
		return transformationDAO.getAllTransformations();
	}

}
