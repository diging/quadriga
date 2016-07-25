package edu.asu.spring.quadriga.service.resolver;

import java.util.List;

import edu.asu.spring.quadriga.domain.resolver.IProjectHandleResolver;
import edu.asu.spring.quadriga.domain.resolver.Status;

public interface IProjectHandleResolverManager {

    boolean saveProjectHandleResolver(IProjectHandleResolver resolver, String username);

    IProjectHandleResolver getProjectHandleResolver(String id);

    List<IProjectHandleResolver> getProjectHandleResolvers(String username);

    /**
     * HandleExample of project handle resolver is validated and the result of
     * validation is returned.
     * 
     * @param resolver
     * @param setResolverValidation
     *            If it's true, the result of validation should be set as
     *            validation field of resolver, otherwise not
     * @return Status
     */
    Status validateProjectResolverHandle(IProjectHandleResolver resolver, boolean setResolverValidation);

}