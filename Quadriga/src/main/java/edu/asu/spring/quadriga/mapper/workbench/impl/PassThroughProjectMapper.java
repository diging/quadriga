package edu.asu.spring.quadriga.mapper.workbench.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.spring.quadriga.domain.impl.passthroughproject.PassThroughProject;
import edu.asu.spring.quadriga.domain.passthroughproject.IPassThroughProject;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.dto.PassThroughProjectDTO;
import edu.asu.spring.quadriga.dto.ProjectDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.mapper.workbench.IPassThroughProjectMapper;

/**
 * The purpose of this class is to map the PassThroughProjectDTO class objects
 * to the PassThroughProject domain objects used in Quadriga
 * 
 */
@Service("passThroughProjectDTOMapper")
public class PassThroughProjectMapper extends ProjectDTOMapper implements IPassThroughProjectMapper {

    /*
     * (non-Javadoc)
     * 
     * @see
     * edu.asu.spring.quadriga.mapper.IPassThroughProjectMapper#getProjectDTO
     * (edu.asu.spring.quadriga.domain.workbench.IProject)
     */
    @Override
    @Transactional
    public ProjectDTO getProjectDTO(IProject project) {
        PassThroughProjectDTO projectDTO = new PassThroughProjectDTO();
        super.fillProjectDTO(project, projectDTO);

        // external project details
        projectDTO.setExternalProjectid(((IPassThroughProject) project).getExternalProjectid());
        projectDTO.setExternalUserId(((IPassThroughProject) project).getExternalUserId());
        projectDTO.setExternalUserName(((IPassThroughProject) project).getExternalUserName());
        projectDTO.setClient(((IPassThroughProject) project).getClient());
        return projectDTO;
    }

    /**
     * Wrapper method for getProjectDTO to minimize casting.
     * 
     * @param project
     *            The project to be mapped to a DTO
     * @return the corresponding DTO
     */
    @Override
    public PassThroughProjectDTO getPassThroughProjectDTO(IPassThroughProject project) {
        return (PassThroughProjectDTO) getProjectDTO(project);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * edu.asu.spring.quadriga.mapper.IPassThroughProjectMapper#getProject(edu
     * .asu.spring.quadriga.dto.ProjectDTO)
     */
    @Override
    @Transactional
    public IProject getProject(ProjectDTO projectDTO) throws QuadrigaStorageException {
        if (projectDTO == null) {
            return null;
        }
        IPassThroughProject project = (IPassThroughProject) super.getProject(projectDTO);
        
        // external project details
        project.setExternalProjectid(((PassThroughProjectDTO) projectDTO).getExternalProjectid());
        project.setExternalUserId(((PassThroughProjectDTO) projectDTO).getExternalUserId());
        project.setExternalUserName(((PassThroughProjectDTO) projectDTO).getExternalUserName());
        project.setClient(((PassThroughProjectDTO) projectDTO).getClient());

        return project;
    }

    @Override
    protected IProject createProjectObject() {
        return new PassThroughProject();
    }

}
