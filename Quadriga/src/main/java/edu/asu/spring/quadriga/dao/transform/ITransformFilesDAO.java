package edu.asu.spring.quadriga.dao.transform;

import java.util.List;

import edu.asu.spring.quadriga.dao.IBaseDAO;
import edu.asu.spring.quadriga.dto.TransformFilesDTO;

public interface ITransformFilesDAO extends IBaseDAO{
	
	public void saveTransformDTO(TransformFilesDTO tranformDTO);
	
	public List<TransformFilesDTO> getAllTransformations();
}
