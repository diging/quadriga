package edu.asu.spring.quadriga.service.impl.uploadtransformation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.service.uploadtransformation.IUploadTransformationManager;
import edu.asu.spring.quadriga.dao.uploadtransformation.IUploadTransformationDAO;
import edu.asu.spring.quadriga.dto.UploadTransfomationFilesDTO;


@Service
public class UploadTransformationManager implements
		IUploadTransformationManager {

	@Autowired
	private IUploadTransformationDAO uploadTransformationDAO;

	@Override
	public void saveAbout(String mappingTitle, String mappingDescription,
			String transformationTitle, String transformationDescription) {
		UploadTransfomationFilesDTO tranformDTO = new UploadTransfomationFilesDTO(
				mappingTitle, mappingDescription, transformationTitle,
				transformationDescription);
		uploadTransformationDAO.saveTransformationFiles(tranformDTO);
		
		

	}

}
