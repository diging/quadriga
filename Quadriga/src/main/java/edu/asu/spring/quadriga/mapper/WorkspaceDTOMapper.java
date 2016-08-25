package edu.asu.spring.quadriga.mapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.factory.workspace.IWorkspaceFactory;
import edu.asu.spring.quadriga.domain.workspace.IWorkSpace;
import edu.asu.spring.quadriga.dto.ConceptCollectionDTO;
import edu.asu.spring.quadriga.dto.DictionaryDTO;
import edu.asu.spring.quadriga.dto.ProjectDTO;
import edu.asu.spring.quadriga.dto.WorkspaceConceptcollectionDTO;
import edu.asu.spring.quadriga.dto.WorkspaceConceptcollectionDTOPK;
import edu.asu.spring.quadriga.dto.WorkspaceDTO;
import edu.asu.spring.quadriga.dto.WorkspaceDictionaryDTO;
import edu.asu.spring.quadriga.dto.WorkspaceDictionaryDTOPK;
import edu.asu.spring.quadriga.dto.WorkspaceEditorDTO;
import edu.asu.spring.quadriga.dto.WorkspaceEditorDTOPK;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.mapper.workbench.IProjectShallowMapper;
import edu.asu.spring.quadriga.service.IUserManager;

@Service
public class WorkspaceDTOMapper extends BaseMapper {

	@Autowired
	private IWorkspaceFactory workspaceFactory;
	
	@Autowired
	private IProjectShallowMapper projectMapper;
	
	@Autowired
    private IUserManager userManager;
	
	public WorkspaceDTO getWorkspaceDTO(IWorkSpace iWorkSpace) {
		WorkspaceDTO workspaceDTO = new WorkspaceDTO();
		workspaceDTO.setWorkspacename(iWorkSpace.getWorkspaceName());
		workspaceDTO.setDescription(iWorkSpace.getDescription());
		workspaceDTO.setWorkspaceowner((getUserDTO(iWorkSpace.getOwner().getUserName())));
		workspaceDTO.setIsarchived(false);
		workspaceDTO.setIsdeactivated(false);
		workspaceDTO.setUpdatedby(iWorkSpace.getOwner().getUserName());
		workspaceDTO.setUpdateddate(new Date());
		workspaceDTO.setCreatedby(iWorkSpace.getOwner().getUserName());
		workspaceDTO.setCreateddate(new Date());
		return workspaceDTO;
	}
	
	public IWorkSpace getWorkSpace(WorkspaceDTO workspaceDTO) throws QuadrigaStorageException
	{
		IWorkSpace workSpace = workspaceFactory.createWorkspaceObject();
		workSpace.setWorkspaceName(workspaceDTO.getWorkspacename());
		workSpace.setDescription(workspaceDTO.getDescription());
		workSpace.setWorkspaceId(workspaceDTO.getWorkspaceid());
		workSpace.setOwner(userManager.getUser(workspaceDTO.getWorkspaceowner().getUsername()));
		ProjectDTO projectDto = workspaceDTO.getProjectWorkspaceDTO().getProjectDTO();
		workSpace.setProject(projectMapper.getProject(projectDto));
		return workSpace;
	}
	
	public List<IWorkSpace> getWorkSpaceList(List<WorkspaceDTO> workspaceDTOList) throws QuadrigaStorageException
	{
		Iterator<WorkspaceDTO> workspaceItr = workspaceDTOList.listIterator();
		List<IWorkSpace> workspaceList = new ArrayList<IWorkSpace>();
		while(workspaceItr.hasNext())
		{
			WorkspaceDTO workspaceDTO = workspaceItr.next();
			workspaceList.add(getWorkSpace(workspaceDTO));
		}
		return workspaceList;
	}
	
	/**
	 * This method associates editor with the workspace supplied
	 * @param workspace
	 * @param userName
	 * @return WorkspaceEditorDTO object
	 * @throws QuadrigaStorageException
	 */
	public WorkspaceEditorDTO getWorkspaceEditor(WorkspaceDTO workspace, String userName) throws QuadrigaStorageException
	{
		WorkspaceEditorDTO workspaceEditor = null;
		WorkspaceEditorDTOPK workspaceEditorKey = null;
		Date date = new Date();
		workspaceEditor = new WorkspaceEditorDTO();
		workspaceEditorKey = new WorkspaceEditorDTOPK(workspace.getWorkspaceid(),userName);
		workspaceEditor.setWorkspaceEditorDTOPK(workspaceEditorKey);
		workspaceEditor.setWorkspaceDTO(workspace);
		workspaceEditor.setQuadrigaUserDTO(getUserDTO(userName));
		workspaceEditor.setCreatedby(userName);
		workspaceEditor.setCreateddate(date);
		workspaceEditor.setUpdatedby(userName);
		workspaceEditor.setUpdateddate(date);
		
		return workspaceEditor;
	}
	
	
	/**
	 * This method associates the concept collection to the given workspace.
	 * @param workspace
	 * @param conceptCollection
	 * @param userName
	 * @return WorkspaceConceptCollectionDTO object
	 */
	public WorkspaceConceptcollectionDTO getWorkspaceConceptCollection(WorkspaceDTO workspace, ConceptCollectionDTO conceptCollection,String userName)
	{
		WorkspaceConceptcollectionDTO workspaceConceptCollection = null;
        WorkspaceConceptcollectionDTOPK workspaceConceptCollectionKey = null;
        Date date = new Date();
        workspaceConceptCollectionKey = new WorkspaceConceptcollectionDTOPK(workspace.getWorkspaceid(),conceptCollection.getConceptCollectionid());
        workspaceConceptCollection = new WorkspaceConceptcollectionDTO();
        workspaceConceptCollection.setWorkspaceConceptcollectionDTOPK(workspaceConceptCollectionKey);
        workspaceConceptCollection.setWorkspaceDTO(workspace);
        workspaceConceptCollection.setConceptCollectionDTO(conceptCollection);
        workspaceConceptCollection.setCreatedby(userName);
        workspaceConceptCollection.setCreateddate(date);
        workspaceConceptCollection.setUpdatedby(userName);
        workspaceConceptCollection.setUpdateddate(date);
		return workspaceConceptCollection;
	}
	
	/**
	 * This method associates the dictionary to the given workspace.
	 * @param workspace
	 * @param dictionary
	 * @param userName
	 * @return WorkspaceConceptCollectionDTO object
	 */
	public WorkspaceDictionaryDTO getWorkspaceDictionary(WorkspaceDTO workspace, DictionaryDTO dictionary, String userName)
	{
		WorkspaceDictionaryDTO workspaceDictionary = null;
		WorkspaceDictionaryDTOPK workspaceDictionaryKey = null;
		Date date = new Date();
		workspaceDictionaryKey = new WorkspaceDictionaryDTOPK(workspace.getWorkspaceid(),dictionary.getDictionaryid());
		workspaceDictionary	= new WorkspaceDictionaryDTO();
		workspaceDictionary.setWorkspaceDictionaryDTOPK(workspaceDictionaryKey);
		workspaceDictionary.setCreatedby(userName);
		workspaceDictionary.setCreateddate(date);
		workspaceDictionary.setUpdatedby(userName);
		workspaceDictionary.setUpdateddate(date);
		workspaceDictionary.setDictionaryDTO(dictionary);
		workspaceDictionary.setWorkspaceDTO(workspace);
		return workspaceDictionary;
	}
}
