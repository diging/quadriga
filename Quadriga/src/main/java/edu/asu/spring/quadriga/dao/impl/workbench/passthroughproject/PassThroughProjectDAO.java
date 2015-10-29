package edu.asu.spring.quadriga.dao.impl.workbench.passthroughproject;

import org.springframework.stereotype.Repository;

import edu.asu.spring.quadriga.dao.impl.BaseDAO;
import edu.asu.spring.quadriga.dao.workbench.passthroughproject.IPassThroughProjectDAO;
import edu.asu.spring.quadriga.dto.PassThroughProjectDTO;

@Repository
public class PassThroughProjectDAO extends BaseDAO<PassThroughProjectDTO> implements IPassThroughProjectDAO {
    @Override
    public PassThroughProjectDTO getDTO(String id) {
        return getDTO(PassThroughProjectDTO.class, id);
    }
}
