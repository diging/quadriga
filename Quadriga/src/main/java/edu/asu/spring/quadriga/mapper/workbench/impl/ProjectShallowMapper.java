package edu.asu.spring.quadriga.mapper.workbench.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.spring.quadriga.dao.workbench.IRetrieveProjectDAO;
import edu.asu.spring.quadriga.domain.proxy.ProjectProxy;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.dto.ProjectDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.mapper.workbench.IProjectShallowMapper;
import edu.asu.spring.quadriga.service.user.mapper.IUserDeepMapper;
import edu.asu.spring.quadriga.service.workbench.IRetrieveProjectManager;

@Service
public class ProjectShallowMapper extends ProjectDTOMapper implements
        IProjectShallowMapper {

    @Autowired
    private IRetrieveProjectDAO dbConnect;

    @Autowired
    private IRetrieveProjectManager projectManager;

    @Autowired
    private IUserDeepMapper userDeepMapper;

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public IProject getProjectDetails(ProjectDTO projectDTO)
            throws QuadrigaStorageException {
        if (projectDTO == null) {
            return null;
        }
        IProject projectProxy = null;

        if (projectDTO != null) {
            projectProxy = new ProjectProxy(projectManager);
            fillProject(projectDTO, projectProxy);
        }

        return projectProxy;
    }
}
