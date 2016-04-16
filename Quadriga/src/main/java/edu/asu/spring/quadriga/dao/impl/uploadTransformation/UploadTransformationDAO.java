package edu.asu.spring.quadriga.dao.impl.uploadTransformation;

import org.springframework.stereotype.Repository;
import edu.asu.spring.quadriga.dao.uploadtransformation.IUploadTransformationDAO;
import edu.asu.spring.quadriga.dao.impl.BaseDAO;
import edu.asu.spring.quadriga.dto.UploadTransfomationFilesDTO;

/**
 * DAO class to upload transformation files
 * 
 * @author JayaVenkat
 *
 */
@Repository
public class UploadTransformationDAO extends
		BaseDAO<UploadTransfomationFilesDTO> implements
		IUploadTransformationDAO {

	@Override
	public UploadTransfomationFilesDTO getDTO(String id) {
		return null;
	}
}
