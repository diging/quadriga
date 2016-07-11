package edu.asu.spring.quadriga.dao.resolver;

import java.util.List;

import org.hibernate.criterion.Restrictions;

import edu.asu.spring.quadriga.dao.IBaseDAO;
import edu.asu.spring.quadriga.dto.ProjectHandleResolverDTO;

public interface IProjectHandleResolverDAO extends IBaseDAO<ProjectHandleResolverDTO> {

    List<ProjectHandleResolverDTO> getProjectResolversForUser(String username);

    
}