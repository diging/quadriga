package edu.asu.spring.quadriga.dao.impl.projectblog;

import edu.asu.spring.quadriga.dao.IBaseDAO;
import edu.asu.spring.quadriga.dto.ProjectBlogDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IProjectBlogDAO extends IBaseDAO<ProjectBlogDTO>  {
    String getCorrespondingProjectID(String projectBlogId)
            throws QuadrigaStorageException;
    
    void addProjectBlogDTO(ProjectBlogDTO projectBlogDTO) throws QuadrigaStorageException;
}
