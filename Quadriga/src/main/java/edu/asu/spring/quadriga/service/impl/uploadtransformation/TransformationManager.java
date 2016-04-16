package edu.asu.spring.quadriga.service.impl.uploadtransformation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.service.uploadtransformation.ITransformationManager;
import edu.asu.spring.quadriga.dao.impl.uploadTransformation.UploadTransformationDAO;
import edu.asu.spring.quadriga.dto.UploadTransfomationFilesDTO;
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
	private UploadTransformationDAO uploadTransformationDAO;

	@Transactional
	@Override
	public void saveMetaData(String mappingTitle, String mappingDescription,
			String mappingFileName, String transformationTitle,
			String transformationDescription, String transformationFileName) {
		UploadTransfomationFilesDTO tranformDTO = new UploadTransfomationFilesDTO(
				mappingTitle, mappingDescription, mappingFileName,
				transformationTitle, transformationDescription,
				transformationFileName);
		tranformDTO.setId(uploadTransformationDAO.generateUniqueID());
		uploadTransformationDAO.saveNewDTO(tranformDTO);

	}

}
