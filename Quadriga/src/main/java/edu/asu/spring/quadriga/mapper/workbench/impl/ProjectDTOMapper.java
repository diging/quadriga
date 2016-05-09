package edu.asu.spring.quadriga.mapper.workbench.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.enums.EProjectAccessibility;
import edu.asu.spring.quadriga.domain.impl.workbench.Project;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.dto.ConceptCollectionDTO;
import edu.asu.spring.quadriga.dto.DictionaryDTO;
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
import edu.asu.spring.quadriga.service.IUserManager;

@Service("ProjectBaseMapper")
public class ProjectDTOMapper extends BaseMapper implements IProjectBaseMapper {

	@Autowired
    private IUserManager userManager;
	
	/* (non-Javadoc)
     * @see edu.asu.spring.quadriga.mapper.IProjectBaseMapper#getProject(edu.asu.spring.quadriga.dto.ProjectDTO)
     */
	@Override
    public IProject getProject(ProjectDTO projectDTO)  throws QuadrigaStorageException {
		IProject project = new Project();
		fillProject(projectDTO, project);
		return project;
	}

    protected void fillProject(ProjectDTO projectDTO, IProject project)
            throws QuadrigaStorageException {
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
	
	/* (non-Javadoc)
     * @see edu.asu.spring.quadriga.mapper.IProjectBaseMapper#getProjectDTO(edu.asu.spring.quadriga.domain.workbench.IProject, java.lang.String)
     */
	@Override
    public ProjectDTO getProjectDTO(IProject project, String userName) {
		QuadrigaUserDTO quadrigaUser = getUserDTO(userName);
		ProjectDTO projectDTO = new ProjectDTO();
		projectDTO.setProjectname(project.getProjectName());
		projectDTO.setDescription(project.getDescription());
		projectDTO.setUnixname(project.getUnixName());
		projectDTO.setProjectid(project.getProjectId());
		projectDTO.setProjectowner(quadrigaUser);
		projectDTO.setCreatedby(project.getOwner().getUserName());
		projectDTO.setCreateddate(new Date());
		projectDTO.setUpdatedby(project.getOwner().getUserName());
		projectDTO.setUpdateddate(new Date());
		if(project.getProjectAccess() != null) {
			projectDTO.setAccessibility(project.getProjectAccess().name());
		}
		return projectDTO;
	}
	
	/* (non-Javadoc)
     * @see edu.asu.spring.quadriga.mapper.IProjectBaseMapper#getProjectEditor(edu.asu.spring.quadriga.dto.ProjectDTO, java.lang.String)
     */
	@Override
    public ProjectEditorDTO getProjectEditor(ProjectDTO project,String userName) throws QuadrigaStorageException
	{
		ProjectEditorDTO projectEditor = null;
		ProjectEditorDTOPK projectEditorKey = null;
		
		String projectId = project.getProjectid();
		Date date = new Date();
		projectEditor = new ProjectEditorDTO();
		projectEditorKey = new ProjectEditorDTOPK(projectId,userName);
		projectEditor.setProjectEditorDTOPK(projectEditorKey);
		projectEditor.setProject(project);
		projectEditor.setQuadrigaUserDTO(getUserDTO(userName));
		projectEditor.setUpdatedby(userName);
		projectEditor.setUpdateddate(date);
		projectEditor.setCreatedby(userName);
		projectEditor.setCreateddate(date);
		return projectEditor;
	}
	
	/* (non-Javadoc)
     * @see edu.asu.spring.quadriga.mapper.IProjectBaseMapper#getProjectDictionary(edu.asu.spring.quadriga.dto.ProjectDTO, edu.asu.spring.quadriga.dto.DictionaryDTO, java.lang.String)
     */
	@Override
    public ProjectDictionaryDTO getProjectDictionary(ProjectDTO project,DictionaryDTO dictionary,String userName)
	{
		ProjectDictionaryDTO projectDictionary = null;
		ProjectDictionaryDTOPK projectDictionaryKey = null;
		Date date = new Date();
		projectDictionary = new ProjectDictionaryDTO();
		projectDictionaryKey = new ProjectDictionaryDTOPK(project.getProjectid(),dictionary.getDictionaryid());
		projectDictionary.setProjectDictionaryDTOPK(projectDictionaryKey);
		projectDictionary.setProject(project);
		projectDictionary.setDictionary(dictionary);
		projectDictionary.setCreatedby(userName);
		projectDictionary.setCreateddate(date);
		projectDictionary.setUpdatedby(userName);
		projectDictionary.setUpdateddate(date);
		return projectDictionary;
	}
	
	/* (non-Javadoc)
     * @see edu.asu.spring.quadriga.mapper.IProjectBaseMapper#getProjectConceptCollection(edu.asu.spring.quadriga.dto.ProjectDTO, edu.asu.spring.quadriga.dto.ConceptCollectionDTO, java.lang.String)
     */
	@Override
    public ProjectConceptCollectionDTO getProjectConceptCollection(ProjectDTO project,ConceptCollectionDTO conceptCollection,String userName)
	{
		ProjectConceptCollectionDTO projectConceptCollection = null;
		ProjectConceptCollectionDTOPK projectConceptCollectionKey = null;
		Date date = new Date();
		projectConceptCollectionKey = new ProjectConceptCollectionDTOPK(project.getProjectid(),conceptCollection.getConceptCollectionid());
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
	
	/* (non-Javadoc)
     * @see edu.asu.spring.quadriga.mapper.IProjectBaseMapper#getProjectWorkspace(edu.asu.spring.quadriga.dto.ProjectDTO, edu.asu.spring.quadriga.dto.WorkspaceDTO)
     */
	@Override
    public ProjectWorkspaceDTO getProjectWorkspace(ProjectDTO project, WorkspaceDTO workspace)
	{
		ProjectWorkspaceDTO projectWorkspace = null;
		ProjectWorkspaceDTOPK projectWorkspaceKey = null;
		String userName = workspace.getCreatedby();
		Date date = new Date();
		projectWorkspaceKey = new ProjectWorkspaceDTOPK(project.getProjectid(),workspace.getWorkspaceid());
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
