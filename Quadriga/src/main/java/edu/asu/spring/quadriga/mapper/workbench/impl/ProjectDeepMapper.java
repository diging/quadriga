package edu.asu.spring.quadriga.mapper.workbench.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.IQuadrigaRole;
import edu.asu.spring.quadriga.domain.factories.ICollaboratorFactory;
import edu.asu.spring.quadriga.domain.factories.IQuadrigaRoleFactory;
import edu.asu.spring.quadriga.domain.factory.workbench.IProjectCollaboratorFactory;
import edu.asu.spring.quadriga.domain.impl.workbench.Project;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.domain.workbench.IProjectCollaborator;
import edu.asu.spring.quadriga.dto.ProjectCollaboratorDTO;
import edu.asu.spring.quadriga.dto.ProjectDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.mapper.workbench.IProjectConceptCollectionShallowMapper;
import edu.asu.spring.quadriga.mapper.workbench.IProjectDeepMapper;
import edu.asu.spring.quadriga.mapper.workbench.IProjectDictionaryShallowMapper;
import edu.asu.spring.quadriga.mapper.workbench.IProjectWorkspaceShallowMapper;
import edu.asu.spring.quadriga.service.IQuadrigaRoleManager;
import edu.asu.spring.quadriga.service.user.mapper.IUserDeepMapper;

@Service
public class ProjectDeepMapper extends ProjectDTOMapper implements
        IProjectDeepMapper {

    @Autowired
    private IUserDeepMapper userDeepMapper;

    @Autowired
    private IProjectCollaboratorFactory projectCollaboratorFactory;

    @Autowired
    private IQuadrigaRoleManager roleManager;

    @Autowired
    private IProjectConceptCollectionShallowMapper projectConceptCollectionShallowMapper;

    @Autowired
    private IProjectDictionaryShallowMapper projectDictionaryShallowMapper;

    @Autowired
    private IProjectWorkspaceShallowMapper projectWorkspaceShallowMapper;

    @Autowired
    private ICollaboratorFactory collaboratorFactory;

    @Autowired
    private IQuadrigaRoleFactory roleFactory;

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public IProject getProject(ProjectDTO projectDTO)
            throws QuadrigaStorageException {
        IProject project = super.getProject(projectDTO);

        // Set List of IProjectCollaborators to the project
        project.setProjectCollaborators(getProjectCollaboratorList(project,
                projectDTO.getCollaboratorList()));
        // Set Project Concept Collections
        project.setProjectConceptCollections(projectConceptCollectionShallowMapper
                .getProjectConceptCollectionList(project,
                        projectDTO.getProjectConceptCollectionDTOList()));
        // Set Project Dictionaries
        project.setProjectDictionaries(projectDictionaryShallowMapper
                .getProjectDictionaryList(project, projectDTO));
        // Set Project Workspaces
        project.setProjectWorkspaces(projectWorkspaceShallowMapper
                .getProjectWorkspaceList(project, projectDTO));

        return project;
    }

    /**
     * This class would help in getting {@link List} of
     * {@link IProjectCollaborator} object mapping for a particular
     * {@link IProject} and {@link ProjectDTO}
     * 
     * @param projectDTO
     *            {@link ProjectDTO} object used for mapping collaborators
     * @param project
     *            {@link IProject} object of domain class type {@link Project}
     * @return Returns {@link List} of {@link IProjectCollaborator} object
     * @throws QuadrigaStorageException
     *             Throws a storage issue when this method is having issues to
     *             access database.
     */
    public List<IProjectCollaborator> getProjectCollaboratorList(
            IProject project, List<ProjectCollaboratorDTO> collaboratorDtoList)
            throws QuadrigaStorageException {
        List<IProjectCollaborator> projectCollaboratorList = new ArrayList<IProjectCollaborator>();
        if (collaboratorDtoList != null && collaboratorDtoList.size() > 0) {
            HashMap<String, IProjectCollaborator> userProjectCollaboratorMap = mapUserProjectCollaborator(
                    project, collaboratorDtoList);
            projectCollaboratorList.addAll(userProjectCollaboratorMap.values());
        }
        return projectCollaboratorList;
    }

    private HashMap<String, IProjectCollaborator> mapUserProjectCollaborator(
            IProject project, List<ProjectCollaboratorDTO> collaboratorDtoList)
            throws QuadrigaStorageException {

        HashMap<String, IProjectCollaborator> userProjectCollaboratorMap = new HashMap<String, IProjectCollaborator>();

        for (ProjectCollaboratorDTO projectCollaboratorDTO : collaboratorDtoList) {
            String userName = projectCollaboratorDTO.getQuadrigaUserDTO()
                    .getUsername();

            if (userProjectCollaboratorMap.containsKey(userName)) {
                String roleName = projectCollaboratorDTO.getCollaboratorDTOPK()
                        .getCollaboratorrole();

                IQuadrigaRole collaboratorRole = roleFactory
                        .createQuadrigaRoleObject();
                collaboratorRole.setDBid(roleName);
                roleManager.fillQuadrigaRole(
                        IQuadrigaRoleManager.PROJECT_ROLES, collaboratorRole);

                IProjectCollaborator projectCollaborator = userProjectCollaboratorMap
                        .get(userName);

                ICollaborator collaborator = projectCollaborator
                        .getCollaborator();
                collaborator.getCollaboratorRoles().add(collaboratorRole);

                // Checking if there is a update latest then previous update
                // date
                if (projectCollaboratorDTO.getUpdateddate().compareTo(
                        projectCollaborator.getUpdatedDate()) > 0) {
                    projectCollaborator.setUpdatedBy(projectCollaboratorDTO
                            .getUpdatedby());
                    projectCollaborator.setUpdateDate(projectCollaboratorDTO
                            .getUpdateddate());
                }

            } else {
                String roleName = projectCollaboratorDTO.getCollaboratorDTOPK()
                        .getCollaboratorrole();
                // Prepare collaborator roles
                IQuadrigaRole collaboratorRole = roleFactory
                        .createQuadrigaRoleObject();
                collaboratorRole.setDBid(roleName);
                roleManager.fillQuadrigaRole(
                        IQuadrigaRoleManager.PROJECT_ROLES, collaboratorRole);
                // Create a Collaborator Role list
                List<IQuadrigaRole> collaboratorRoleList = new ArrayList<IQuadrigaRole>();
                // Add collaborator role to the list
                collaboratorRoleList.add(collaboratorRole);
                // Create a Collaborator
                ICollaborator collaborator = collaboratorFactory
                        .createCollaborator();
                // Set Collaborator Role List to the Collaborator
                collaborator.setCollaboratorRoles(collaboratorRoleList);
                collaborator.setUserObj(userDeepMapper.getUser(userName));

                // Create ProjectCollaborator object
                IProjectCollaborator projectCollaborator = projectCollaboratorFactory
                        .createProjectCollaboratorObject();
                projectCollaborator.setCollaborator(collaborator);
                projectCollaborator.setCreatedBy(projectCollaboratorDTO
                        .getCreatedby());
                projectCollaborator.setCreatedDate(projectCollaboratorDTO
                        .getCreateddate());
                projectCollaborator.setUpdatedBy(projectCollaboratorDTO
                        .getUpdatedby());
                projectCollaborator.setUpdateDate(projectCollaboratorDTO
                        .getUpdateddate());
                projectCollaborator.setProject(project);

                userProjectCollaboratorMap.put(userName, projectCollaborator);

            }
        }
        return userProjectCollaboratorMap;
    }

}
