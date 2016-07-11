package edu.asu.spring.quadriga.mapper.resolver.impl;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.resolver.IProjectHandleResolver;
import edu.asu.spring.quadriga.domain.resolver.impl.ProjectHandleResolver;
import edu.asu.spring.quadriga.dto.ProjectHandleResolverDTO;
import edu.asu.spring.quadriga.mapper.resolver.IProjectHandleResolverMapper;

@Service
public class ProjectHandleResolverMapper implements IProjectHandleResolverMapper {

    /* (non-Javadoc)
     * @see edu.asu.spring.quadriga.mapper.resolver.impl.IProjectHandleResolverMapper#mapProjectHandleResolver(edu.asu.spring.quadriga.dto.ProjectHandleResolverDTO)
     */
    @Override
    public IProjectHandleResolver mapProjectHandleResolver(ProjectHandleResolverDTO dto) {
        IProjectHandleResolver resolver = new ProjectHandleResolver();
        
        resolver.setDescription(dto.getDescription());
        resolver.setHandleExample(dto.getHandleExample());
        resolver.setHandlePattern(dto.getHandlePattern());
        resolver.setId(dto.getId());
        resolver.setProjectName(dto.getProjectName());
        resolver.setProjectUrl(dto.getProjectUrl());
        resolver.setResolvedHandlePattern(dto.getResolvedHandlePattern());
        resolver.setUsername(dto.getUsername());
        resolver.setResolvedHandleExample(dto.getResolvedHandleExample());
        
        return resolver;
    }
    
    /* (non-Javadoc)
     * @see edu.asu.spring.quadriga.mapper.resolver.impl.IProjectHandleResolverMapper#mapProjectHandleResolver(edu.asu.spring.quadriga.domain.resolver.impl.IProjectHandleResolver)
     */
    @Override
    public ProjectHandleResolverDTO mapProjectHandleResolver(IProjectHandleResolver resolver) {
        ProjectHandleResolverDTO dto = new ProjectHandleResolverDTO();
        
        dto.setDescription(resolver.getDescription());
        dto.setHandleExample(resolver.getHandleExample());
        dto.setHandlePattern(resolver.getHandlePattern());
        dto.setId(resolver.getId());
        dto.setProjectName(resolver.getProjectName());
        dto.setProjectUrl(resolver.getProjectUrl());
        dto.setResolvedHandlePattern(resolver.getResolvedHandlePattern());
        dto.setUsername(resolver.getUsername());
        dto.setResolvedHandleExample(resolver.getResolvedHandleExample());
        
        return dto;
    }
}
