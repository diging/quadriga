package edu.asu.spring.quadriga.dao.impl.transform;

import java.util.List;

import org.springframework.stereotype.Repository;

import edu.asu.spring.quadriga.dao.impl.BaseDAO;
import edu.asu.spring.quadriga.dao.transform.ITransformFilesDAO;
import edu.asu.spring.quadriga.dto.TransformFilesDTO;

/**
 * DAO class to save , get data from tbl_transfomationfiles_metadata
 * 
 * @author JayaVenkat
 *
 */
@Repository
public class TransformFilesDAO extends BaseDAO implements ITransformFilesDAO {

    /**
     * This method will return list of DTO objects and each DTO object
     * corresponds to one transformation
     */
    @Override
    public List<TransformFilesDTO> getAllTransformations() {
        List<TransformFilesDTO> transformList = sessionFactory
                .getCurrentSession().createCriteria(TransformFilesDTO.class)
                .list();
        return transformList;
    }

    @Override
    public Object getDTO(String id) {
        return getDTO(TransformFilesDTO.class, id);
    }

}
