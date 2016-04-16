package edu.asu.spring.quadriga.service.impl.uploadtransformation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.service.uploadtransformation.ITransformationManager;
import edu.asu.spring.quadriga.dao.impl.uploadTransformation.UploadTransformationDAO;
import edu.asu.spring.quadriga.dao.uploadtransformation.IUploadTransformationDAO;
import edu.asu.spring.quadriga.dto.UploadTransfomationFilesDTO;
import org.springframework.transaction.annotation.Transactional;


@Service
public class TransformationManager implements
		ITransformationManager {

	@Autowired
	private UploadTransformationDAO uploadTransformationDAO;

	@Transactional
	@Override
	public void saveMetaData(String mappingTitle, String mappingDescription,
			String transformationTitle, String transformationDescription) {
		UploadTransfomationFilesDTO tranformDTO = new UploadTransfomationFilesDTO(
				mappingTitle, mappingDescription, transformationTitle,
				transformationDescription);
		tranformDTO.setId(uploadTransformationDAO.generateUniqueID());
		uploadTransformationDAO.saveNewDTO(tranformDTO);
				
	}

}
