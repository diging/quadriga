package edu.asu.spring.quadriga.service.resolver;

import java.util.List;

import edu.asu.spring.quadriga.domain.resolver.IProjectHandleResolver;

public interface IProjectHandleResolverManager {

    boolean saveProjectHandleResolver(IProjectHandleResolver resolver, String username);

    IProjectHandleResolver getProjectHandleResolver(String id);

    List<IProjectHandleResolver> getProjectHandleResolvers(String username);

    boolean deleteProjectHandleResolver(IProjectHandleResolver resolver);

}