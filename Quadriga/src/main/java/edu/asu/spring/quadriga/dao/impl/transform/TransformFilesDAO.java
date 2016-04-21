package edu.asu.spring.quadriga.dao.impl.transform;

import org.springframework.stereotype.Repository;
import java.util.List;
import edu.asu.spring.quadriga.dao.transform.ITransformFilesDAO;
import edu.asu.spring.quadriga.dao.impl.BaseDAO;
import edu.asu.spring.quadriga.dto.TransformFilesDTO;

/**
 * DAO class to upload transformation files
 * 
 * @author JayaVenkat
 *
 */
@Repository
public class TransformFilesDAO extends BaseDAO implements
		ITransformFilesDAO {

	@Override
	public Object getDTO(String id) {		
		return getDTO(TransformFilesDTO.class, id);
	}

	@Override
	public void saveTransformDTO(TransformFilesDTO tranformDTO) {
		saveNewDTO(tranformDTO);				
	}
	
	@Override
	public List<TransformFilesDTO> getAllTransformations(){
		List<TransformFilesDTO> transformList = sessionFactory.getCurrentSession().createCriteria(TransformFilesDTO.class).list();
		return transformList;
	}

	
}
