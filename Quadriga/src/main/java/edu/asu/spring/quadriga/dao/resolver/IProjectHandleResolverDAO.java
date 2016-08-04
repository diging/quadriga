package edu.asu.spring.quadriga.dao.resolver;

import java.util.List;

import edu.asu.spring.quadriga.dao.IBaseDAO;
import edu.asu.spring.quadriga.dto.ProjectDTO;
import edu.asu.spring.quadriga.dto.ProjectHandleResolverDTO;

public interface IProjectHandleResolverDAO extends IBaseDAO<ProjectHandleResolverDTO> {

    List<ProjectHandleResolverDTO> getProjectResolversForUser(String username);

    public List<ProjectDTO> getProjectsForResolverId(String resolverId);

}