package edu.asu.spring.quadriga.mapper.resolver;

import edu.asu.spring.quadriga.domain.resolver.IProjectHandleResolver;
import edu.asu.spring.quadriga.dto.ProjectHandleResolverDTO;

public interface IProjectHandleResolverMapper {

    IProjectHandleResolver mapProjectHandleResolver(ProjectHandleResolverDTO dto);

    ProjectHandleResolverDTO mapProjectHandleResolver(IProjectHandleResolver resolver);

}