package edu.asu.spring.quadriga.mapper.workbench.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.dto.ProjectDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.mapper.workbench.IProjectConceptCollectionShallowMapper;
import edu.asu.spring.quadriga.mapper.workbench.IProjectDeepMapper;
import edu.asu.spring.quadriga.mapper.workbench.IProjectDictionaryShallowMapper;
import edu.asu.spring.quadriga.mapper.workspace.IWorkspaceShallowMapper;

@Service
public class ProjectDeepMapper extends ProjectDTOMapper implements
        IProjectDeepMapper {

    
    @Autowired
    private IProjectConceptCollectionShallowMapper projectConceptCollectionShallowMapper;

    @Autowired
    private IProjectDictionaryShallowMapper projectDictionaryShallowMapper;

    @Autowired
    private IWorkspaceShallowMapper wsShallowMapper;

    
    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public IProject getProject(ProjectDTO projectDTO)
            throws QuadrigaStorageException {
        IProject project = super.getProject(projectDTO);

        // Set Project Concept Collections
        project.setProjectConceptCollections(projectConceptCollectionShallowMapper
                .getProjectConceptCollectionList(project,
                        projectDTO.getProjectConceptCollectionDTOList()));
        // Set Project Dictionaries
        project.setProjectDictionaries(projectDictionaryShallowMapper
                .getProjectDictionaryList(project, projectDTO));
        // Set Project Workspaces
        project.setProjectWorkspaces(wsShallowMapper
                .getProjectWorkspaceList(project, projectDTO.getProjectWorkspaceDTOList()));
        return project;
    }

    

}
