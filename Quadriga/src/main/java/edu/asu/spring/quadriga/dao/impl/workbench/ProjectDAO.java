package edu.asu.spring.quadriga.dao.impl.workbench;

import org.springframework.stereotype.Repository;

import edu.asu.spring.quadriga.dao.impl.BaseDAO;
import edu.asu.spring.quadriga.dao.workbench.IProjectDAO;
import edu.asu.spring.quadriga.dto.ProjectDTO;

@Repository
public class ProjectDAO extends BaseDAO<ProjectDTO> implements IProjectDAO {

    @Override
    public ProjectDTO getProjectDTO(String id) {
       return getDTO(ProjectDTO.class, id);
    }
    
}
