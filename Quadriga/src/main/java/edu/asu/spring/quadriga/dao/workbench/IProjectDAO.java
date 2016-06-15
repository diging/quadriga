package edu.asu.spring.quadriga.dao.workbench;

import edu.asu.spring.quadriga.dao.IBaseDAO;
import edu.asu.spring.quadriga.dto.ProjectDTO;

public interface IProjectDAO extends IBaseDAO<ProjectDTO> {

    public abstract ProjectDTO getProjectDTO(String id);

}