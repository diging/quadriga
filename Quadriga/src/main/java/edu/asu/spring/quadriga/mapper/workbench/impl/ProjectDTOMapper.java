package edu.asu.spring.quadriga.mapper.workbench.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.IQuadrigaRole;
import edu.asu.spring.quadriga.domain.enums.EProjectAccessibility;
import edu.asu.spring.quadriga.domain.factories.ICollaboratorFactory;
import edu.asu.spring.quadriga.domain.factories.IQuadrigaRoleFactory;
import edu.asu.spring.quadriga.domain.factory.workbench.IProjectCollaboratorFactory;
import edu.asu.spring.quadriga.domain.impl.workbench.Project;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.domain.workbench.IProjectCollaborator;
import edu.asu.spring.quadriga.dto.ConceptCollectionDTO;
import edu.asu.spring.quadriga.dto.DictionaryDTO;
import edu.asu.spring.quadriga.dto.ProjectCollaboratorDTO;
import edu.asu.spring.quadriga.dto.ProjectConceptCollectionDTO;
import edu.asu.spring.quadriga.dto.ProjectConceptCollectionDTOPK;
import edu.asu.spring.quadriga.dto.ProjectDTO;
import edu.asu.spring.quadriga.dto.ProjectDictionaryDTO;
import edu.asu.spring.quadriga.dto.ProjectDictionaryDTOPK;
import edu.asu.spring.quadriga.dto.ProjectEditorDTO;
import edu.asu.spring.quadriga.dto.ProjectEditorDTOPK;
import edu.asu.spring.quadriga.dto.ProjectWorkspaceDTO;
import edu.asu.spring.quadriga.dto.ProjectWorkspaceDTOPK;
import edu.asu.spring.quadriga.dto.QuadrigaUserDTO;
import edu.asu.spring.quadriga.dto.WorkspaceDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.mapper.BaseMapper;
import edu.asu.spring.quadriga.mapper.workbench.IProjectBaseMapper;
import edu.asu.spring.quadriga.service.IQuadrigaRoleManager;
import edu.asu.spring.quadriga.service.IUserManager;
import edu.asu.spring.quadriga.service.user.mapper.IUserDeepMapper;

@Service("ProjectBaseMapper")
public class ProjectDTOMapper extends BaseMapper implements IProjectBaseMapper {

    @Autowired
    private IUserManager userManager;
    
    @Autowired
    private ICollaboratorFactory collaboratorFactory;

    @Autowired
    private IQuadrigaRoleFactory roleFactory;

    @Autowired
    private IUserDeepMapper userDeepMapper;

    @Autowired
    private IProjectCollaboratorFactory projectCollaboratorFactory;

    @Autowired
    private IQuadrigaRoleManager roleManager;


    /*
     * (non-Javadoc)
     * 
     * @see
     * edu.asu.spring.quadriga.mapper.IProjectBaseMapper#getProject(edu.asu.
     * spring.quadriga.dto.ProjectDTO)
     */
    @Override
    @Transactional
    public IProject getProject(ProjectDTO projectDTO) throws QuadrigaStorageException {
        if (projectDTO == null) {
            return null;
        }
        IProject project = createProjectObject();
        fillProject(projectDTO, project);
        
     // Set List of IProjectCollaborators to the project
        project.setProjectCollaborators(getProjectCollaboratorList(project,
                projectDTO.getCollaboratorList()));
        
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
    @Transactional
    protected List<IProjectCollaborator> getProjectCollaboratorList(
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

    protected IProject createProjectObject() {
        IProject project = new Project();
        return project;
    }

    protected void fillProject(ProjectDTO projectDTO, IProject project) throws QuadrigaStorageException {
        project.setProjectName(projectDTO.getProjectname());
        project.setDescription(projectDTO.getDescription());
        project.setUnixName(projectDTO.getUnixname());
        project.setProjectId(projectDTO.getProjectid());
        project.setCreatedBy(projectDTO.getCreatedby());
        project.setCreatedDate(projectDTO.getCreateddate());
        project.setUpdatedBy(projectDTO.getUpdatedby());
        project.setUpdatedDate(projectDTO.getUpdateddate());
        project.setOwner(userManager.getUser(projectDTO.getProjectowner().getUsername()));
        project.setProjectAccess(EProjectAccessibility.valueOf(projectDTO.getAccessibility()));
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * edu.asu.spring.quadriga.mapper.IProjectBaseMapper#getProjectDTO(edu.asu
     * .spring.quadriga.domain.workbench.IProject, java.lang.String)
     */
    @Override
    @Transactional
    public ProjectDTO getProjectDTO(IProject project) {
        ProjectDTO projectDTO = new ProjectDTO();
        fillProjectDTO(project, projectDTO);
        return projectDTO;
    }

    protected void fillProjectDTO(IProject project, ProjectDTO projectDTO) {
        projectDTO.setProjectname(project.getProjectName());
        projectDTO.setDescription(project.getDescription());
        projectDTO.setUnixname(project.getUnixName());
        projectDTO.setProjectid(project.getProjectId());

        QuadrigaUserDTO quadrigaUser = getUserDTO(project.getCreatedBy());
        projectDTO.setProjectowner(quadrigaUser);
        projectDTO.setCreatedby(project.getCreatedBy());
        projectDTO.setCreateddate(new Date());
        projectDTO.setUpdatedby(project.getUpdatedBy());
        projectDTO.setUpdateddate(new Date());

        if (project.getProjectAccess() != null) {
            projectDTO.setAccessibility(project.getProjectAccess().name());
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * edu.asu.spring.quadriga.mapper.IProjectBaseMapper#getProjectEditor(edu
     * .asu.spring.quadriga.dto.ProjectDTO, java.lang.String)
     */
    @Override
    public ProjectEditorDTO getProjectEditor(ProjectDTO project, String userName) throws QuadrigaStorageException {
        ProjectEditorDTO projectEditor = null;
        ProjectEditorDTOPK projectEditorKey = null;

        String projectId = project.getProjectid();
        Date date = new Date();
        projectEditor = new ProjectEditorDTO();
        projectEditorKey = new ProjectEditorDTOPK(projectId, userName);
        projectEditor.setProjectEditorDTOPK(projectEditorKey);
        projectEditor.setProject(project);
        projectEditor.setQuadrigaUserDTO(getUserDTO(userName));
        projectEditor.setUpdatedby(userName);
        projectEditor.setUpdateddate(date);
        projectEditor.setCreatedby(userName);
        projectEditor.setCreateddate(date);
        return projectEditor;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * edu.asu.spring.quadriga.mapper.IProjectBaseMapper#getProjectDictionary
     * (edu.asu.spring.quadriga.dto.ProjectDTO,
     * edu.asu.spring.quadriga.dto.DictionaryDTO, java.lang.String)
     */
    @Override
    public ProjectDictionaryDTO getProjectDictionary(ProjectDTO project, DictionaryDTO dictionary, String userName) {
        ProjectDictionaryDTO projectDictionary = null;
        ProjectDictionaryDTOPK projectDictionaryKey = null;
        Date date = new Date();
        projectDictionary = new ProjectDictionaryDTO();
        projectDictionaryKey = new ProjectDictionaryDTOPK(project.getProjectid(), dictionary.getDictionaryid());
        projectDictionary.setProjectDictionaryDTOPK(projectDictionaryKey);
        projectDictionary.setProject(project);
        projectDictionary.setDictionary(dictionary);
        projectDictionary.setCreatedby(userName);
        projectDictionary.setCreateddate(date);
        projectDictionary.setUpdatedby(userName);
        projectDictionary.setUpdateddate(date);
        return projectDictionary;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * edu.asu.spring.quadriga.mapper.IProjectBaseMapper#getProjectConceptCollection
     * (edu.asu.spring.quadriga.dto.ProjectDTO,
     * edu.asu.spring.quadriga.dto.ConceptCollectionDTO, java.lang.String)
     */
    @Override
    public ProjectConceptCollectionDTO getProjectConceptCollection(ProjectDTO project,
            ConceptCollectionDTO conceptCollection, String userName) {
        ProjectConceptCollectionDTO projectConceptCollection = null;
        ProjectConceptCollectionDTOPK projectConceptCollectionKey = null;
        Date date = new Date();
        projectConceptCollectionKey = new ProjectConceptCollectionDTOPK(project.getProjectid(),
                conceptCollection.getConceptCollectionid());
        projectConceptCollection = new ProjectConceptCollectionDTO();

        projectConceptCollection.setProjectConceptcollectionDTOPK(projectConceptCollectionKey);
        projectConceptCollection.setProjectDTO(project);
        projectConceptCollection.setConceptCollection(conceptCollection);
        projectConceptCollection.setCreatedby(userName);
        projectConceptCollection.setCreateddate(date);
        projectConceptCollection.setUpdatedby(userName);
        projectConceptCollection.setUpdateddate(date);

        return projectConceptCollection;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * edu.asu.spring.quadriga.mapper.IProjectBaseMapper#getProjectWorkspace
     * (edu.asu.spring.quadriga.dto.ProjectDTO,
     * edu.asu.spring.quadriga.dto.WorkspaceDTO)
     */
    @Override
    public ProjectWorkspaceDTO getProjectWorkspace(ProjectDTO project, WorkspaceDTO workspace) {
        ProjectWorkspaceDTO projectWorkspace = null;
        ProjectWorkspaceDTOPK projectWorkspaceKey = null;
        String userName = workspace.getCreatedby();
        Date date = new Date();
        projectWorkspaceKey = new ProjectWorkspaceDTOPK(project.getProjectid(), workspace.getWorkspaceid());
        projectWorkspace = new ProjectWorkspaceDTO();
        projectWorkspace.setProjectWorkspaceDTOPK(projectWorkspaceKey);
        projectWorkspace.setProjectDTO(project);
        projectWorkspace.setWorkspaceDTO(workspace);
        projectWorkspace.setCreatedby(userName);
        projectWorkspace.setCreateddate(date);
        projectWorkspace.setUpdatedby(userName);
        projectWorkspace.setUpdateddate(date);

        return projectWorkspace;
    }
}
