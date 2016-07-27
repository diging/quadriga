package edu.asu.spring.quadriga.service.resolver.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.spring.quadriga.dao.resolver.IProjectHandleResolverDAO;
import edu.asu.spring.quadriga.domain.resolver.IProjectHandleResolver;
import edu.asu.spring.quadriga.dto.ProjectHandleResolverDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaException;
import edu.asu.spring.quadriga.mapper.resolver.IProjectHandleResolverMapper;
import edu.asu.spring.quadriga.service.resolver.IProjectHandleResolverManager;

/**
 * Manager class for {@link IProjectHandleResolver}s.
 * 
 * @author jdamerow
 *
 */
@Service
public class ProjectHandleResolverManager implements IProjectHandleResolverManager {

    @Autowired
    private IProjectHandleResolverDAO resolverDao;

    @Autowired
    private IProjectHandleResolverMapper mapper;

    /**
     * Saves or updates and object in the database.
     * 
     * @param resolver
     *            The project handle resolver to save or update.
     * @param username
     *            The username of the user that created the resolver.
     */
    @Override
    @Transactional
    public boolean saveProjectHandleResolver(IProjectHandleResolver resolver, String username) {
        if (resolver.getId() == null) {
            String id = resolverDao.generateUniqueID();
            resolver.setId(id);
            resolver.setUsername(username);
        }

        ProjectHandleResolverDTO resolverDto = mapper.mapProjectHandleResolver(resolver);
        resolverDao.saveOrUpdateDTO(resolverDto);

        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see edu.asu.spring.quadriga.service.resolver.impl.
     * IProjectHandleResolverManager#getProjectHandleResolver(java.lang.String)
     */
    @Override
    public IProjectHandleResolver getProjectHandleResolver(String id) {
        ProjectHandleResolverDTO dto = resolverDao.getDTO(id);
        return mapper.mapProjectHandleResolver(dto);
    }

    /*
     * (non-Javadoc)
     * 
     * @see edu.asu.spring.quadriga.service.resolver.impl.
     * IProjectHandleResolverManager#getProjectHandleResolvers(java.lang.String)
     */
    @Override
    public List<IProjectHandleResolver> getProjectHandleResolvers(String username) {
        List<ProjectHandleResolverDTO> resolverDtos = resolverDao.getProjectResolversForUser(username);

        List<IProjectHandleResolver> resolvers = new ArrayList<IProjectHandleResolver>();
        resolverDtos.forEach(dto -> resolvers.add(mapper.mapProjectHandleResolver(dto)));

        return resolvers;
    }

    /**
     * Checks whether the given resolver is used by any project and if it's
     * true, throws exception indicating the error. If no project is using the
     * resolver, then the resolver is deleted from the database.
     * 
     * @param resolver
     *            The project handle resolver to delete.
     * @throws QuadrigaException
     */
    @Override
    @Transactional
    public boolean deleteProjectHandleResolver(IProjectHandleResolver resolver) throws QuadrigaException {

        if (resolverDao.getProjectsForResolverId(resolver.getId()).size() > 0) {
            throw new QuadrigaException("The given resolver cannot be deleted as it is being used by Project.");
        }

        ProjectHandleResolverDTO resolverDto = mapper.mapProjectHandleResolver(resolver);
        resolverDao.deleteDTO(resolverDto);

        return true;
    }
}