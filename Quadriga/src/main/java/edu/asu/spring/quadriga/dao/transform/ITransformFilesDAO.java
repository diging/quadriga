package edu.asu.spring.quadriga.dao.transform;

import java.util.List;

import edu.asu.spring.quadriga.dao.IBaseDAO;
import edu.asu.spring.quadriga.dto.TransformFilesDTO;

public interface ITransformFilesDAO extends IBaseDAO {

    public List<TransformFilesDTO> getAllTransformations();
}
