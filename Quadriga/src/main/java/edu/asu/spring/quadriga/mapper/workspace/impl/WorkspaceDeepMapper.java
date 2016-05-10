package edu.asu.spring.quadriga.mapper.workspace.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.spring.quadriga.dao.workspace.IWorkspaceDAO;
import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.IQuadrigaRole;
import edu.asu.spring.quadriga.domain.factories.ICollaboratorFactory;
import edu.asu.spring.quadriga.domain.factories.IQuadrigaRoleFactory;
import edu.asu.spring.quadriga.domain.factory.workspace.IWorkspaceCollaboratorFactory;
import edu.asu.spring.quadriga.domain.factory.workspace.IWorkspaceFactory;
import edu.asu.spring.quadriga.domain.workspace.IWorkSpace;
import edu.asu.spring.quadriga.domain.workspace.IWorkspaceCollaborator;
import edu.asu.spring.quadriga.dto.ExternalWorkspaceDTO;
import edu.asu.spring.quadriga.dto.WorkspaceCollaboratorDTO;
import edu.asu.spring.quadriga.dto.WorkspaceDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.mapper.workspace.IWorkspaceCCShallowMapper;
import edu.asu.spring.quadriga.mapper.workspace.IWorkspaceDeepMapper;
import edu.asu.spring.quadriga.mapper.workspace.IWorkspaceDictionaryShallowMapper;
import edu.asu.spring.quadriga.service.IQuadrigaRoleManager;
import edu.asu.spring.quadriga.service.network.mapper.IWorkspaceNetworkMapper;
import edu.asu.spring.quadriga.service.user.mapper.IUserDeepMapper;

/**
 * This class would help in mapping {@link IWorkSpace} object with all the variables in it using {@link WorkspaceDTO}.
 * @author Lohith Dwaraka
 *
 */
@Service
public class WorkspaceDeepMapper extends BaseWorkspaceMapper implements IWorkspaceDeepMapper  {

	@Autowired
	private IWorkspaceDAO wsDao;

	@Autowired
	private IWorkspaceCCShallowMapper workspaceCCShallowMapper;

	@Autowired
	private IWorkspaceDictionaryShallowMapper workspaceDictionaryShallowMapper;

	@Autowired
	private IWorkspaceFactory workspaceFactory;

	@Autowired
	private IWorkspaceCollaboratorFactory workspaceCollaboratorFactory;

	@Autowired
	private IWorkspaceNetworkMapper workspaceNetworkMapper;

	@Autowired
	private IQuadrigaRoleManager roleManager;

	@Autowired
	private ICollaboratorFactory collaboratorFactory;

	@Autowired
	private IQuadrigaRoleFactory roleFactory;

	@Autowired
	private IUserDeepMapper userDeepMapper;


	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public IWorkSpace mapWorkspaceDTO(WorkspaceDTO workspaceDTO) throws QuadrigaStorageException{

		IWorkSpace workspace = null;

		if(workspaceDTO != null){
			workspace = workspaceFactory.createWorkspaceObject();
			fillWorkspace(workspaceDTO, workspace);		
		}

		return workspace;

	}


    @Override
    public void fillWorkspace(WorkspaceDTO workspaceDTO, IWorkSpace workspace) throws QuadrigaStorageException {
        workspace.setWorkspaceId(workspaceDTO.getWorkspaceid());
        workspace.setWorkspaceName(workspaceDTO.getWorkspacename());
        workspace.setDescription(workspaceDTO.getDescription());
        workspace.setOwner(userDeepMapper.getUser(workspaceDTO.getWorkspaceowner().getUsername()));
        workspace.setCreatedBy(workspaceDTO.getCreatedby());
        workspace.setCreatedDate(workspaceDTO.getCreateddate());
        workspace.setUpdatedBy(workspaceDTO.getUpdatedby());
        workspace.setUpdatedDate(workspaceDTO.getUpdateddate());
        
        // Set Workspace Collaborators
        workspace.setWorkspaceCollaborators(getWorkspaceCollaboratorList(workspaceDTO, workspace));
        // Set Workspace Concept Collations
        workspace.setWorkspaceConceptCollections(workspaceCCShallowMapper.getWorkspaceCCList(workspace, workspaceDTO));
        // Set Workspace Dictionaries
        workspace.setWorkspaceDictionaries(workspaceDictionaryShallowMapper.getWorkspaceDictionaryList(workspace, workspaceDTO));

        // Set Project Workspace 
        workspace.setProjectWorkspace(getProjectWorkspaceOfWorkspace(workspace, workspaceDTO));

        //Set network workspace
        workspace.setWorkspaceNetworks(workspaceNetworkMapper.getNetworkWorkspaceByWorkSpaceDTO(workspaceDTO, workspace));
             
        if (workspaceDTO instanceof ExternalWorkspaceDTO) {
            workspace.setExternalWorkspaceId(((ExternalWorkspaceDTO) workspaceDTO).getExternalWorkspaceid());
        }
    }

	/**
	 * This class should returns a {@link List} of {@link IWorkspaceCollaborator} object by mapping workspaceDTO details and {@link List} of {@link WorkspaceCollaboratorDTO}
	 * @param workspaceDTO										{@link WorkspaceDTO} object of a workspace
	 * @param workspace											{@link IWorkspace} object to refer workspace object in List of {@link IWorkspaceCollaborator}
	 * @return													Returns a {@link List} of {@link IWorkspaceCollaborator} objects
	 * @throws QuadrigaStorageException							Throws a storage exception when method has issue to fetch data from database
	 */
	private List<IWorkspaceCollaborator> getWorkspaceCollaboratorList(WorkspaceDTO workspaceDTO,IWorkSpace workspace) throws QuadrigaStorageException
	{
		List<IWorkspaceCollaborator> workspaceCollaboratorList = null;
		if(workspaceDTO.getWorkspaceCollaboratorDTOList() != null && workspaceDTO.getWorkspaceCollaboratorDTOList().size() > 0)
		{
			HashMap<String,IWorkspaceCollaborator> userWorkspaceCollaboratorMap = mapUserWorkspaceCollaborator(workspaceDTO,workspace);
			for(String userID:userWorkspaceCollaboratorMap.keySet())
			{
				if(workspaceCollaboratorList == null){
					workspaceCollaboratorList = new ArrayList<IWorkspaceCollaborator>();
				}
				workspaceCollaboratorList.add(userWorkspaceCollaboratorMap.get(userID));
			}
		}
		return workspaceCollaboratorList;
	}

	/**
	 * This class should map workspace collaborators based on user id using {@link WorkspaceDTO} and {@link IWorkSpace} object.
	 * @param workspaceDTO									{@link WorkspaceDTO} object for mapping collaborator
	 * @param workspace										{@link IWorkSpace} object 
	 * @return												Returns {@link HashMap} of {@link String} and {@link IWorkspaceCollaborator}
	 * @throws QuadrigaStorageException 
	 */
	private HashMap<String,IWorkspaceCollaborator> mapUserWorkspaceCollaborator(WorkspaceDTO workspaceDTO,IWorkSpace workspace) throws QuadrigaStorageException
	{		

		HashMap<String, IWorkspaceCollaborator> userWorkspaceCollaboratorMap = new HashMap<String, IWorkspaceCollaborator>();

		for(WorkspaceCollaboratorDTO workspaceCollaboratorDTO : workspaceDTO.getWorkspaceCollaboratorDTOList())
		{
			String userName = workspaceCollaboratorDTO.getQuadrigaUserDTO().getUsername();

			if(userWorkspaceCollaboratorMap.containsKey(userName))
			{
				String roleName = workspaceCollaboratorDTO.getCollaboratorDTOPK().getCollaboratorrole();

				IQuadrigaRole collaboratorRole = roleFactory.createQuadrigaRoleObject();
				collaboratorRole.setDBid(roleName);
				roleManager.fillQuadrigaRole(IQuadrigaRoleManager.WORKSPACE_ROLES, collaboratorRole);

				IWorkspaceCollaborator workspaceCollaborator =userWorkspaceCollaboratorMap.get(userName);

				ICollaborator collaborator = workspaceCollaborator.getCollaborator();
				collaborator.getCollaboratorRoles().add(collaboratorRole);

				// Checking if there is a update latest then previous update date 
				if(workspaceCollaboratorDTO.getUpdateddate().compareTo(workspaceCollaborator.getUpdatedDate()) > 0 ){
					workspaceCollaborator.setUpdatedBy(workspaceCollaboratorDTO.getUpdatedby());
					workspaceCollaborator.setUpdatedDate(workspaceCollaboratorDTO.getUpdateddate());
				}

			}
			else
			{
				String roleName = workspaceCollaboratorDTO.getCollaboratorDTOPK().getCollaboratorrole();
				// Prepare collaborator roles
				IQuadrigaRole collaboratorRole = roleFactory.createQuadrigaRoleObject();
				collaboratorRole.setDBid(roleName);
				roleManager.fillQuadrigaRole(IQuadrigaRoleManager.WORKSPACE_ROLES, collaboratorRole);
				// Create a Collaborator Role list
				List<IQuadrigaRole> collaboratorRoleList = new ArrayList<IQuadrigaRole>();
				// Add collaborator role to the list
				collaboratorRoleList.add(collaboratorRole);
				// Create a Collaborator
				ICollaborator collaborator = collaboratorFactory.createCollaborator();
				// Set Collaborator Role List to the Collaborator
				collaborator.setCollaboratorRoles(collaboratorRoleList);
				collaborator.setUserObj(userDeepMapper.getUser(userName));
				
				// Create ProjectCollaborator object
				IWorkspaceCollaborator workspaceCollaborator = workspaceCollaboratorFactory.createWorkspaceCollaboratorObject();
				workspaceCollaborator.setCollaborator(collaborator);
				workspaceCollaborator.setCreatedBy(workspaceCollaboratorDTO.getCreatedby());
				workspaceCollaborator.setCreatedDate(workspaceCollaboratorDTO.getCreateddate());
				workspaceCollaborator.setUpdatedBy(workspaceCollaboratorDTO.getUpdatedby());
				workspaceCollaborator.setUpdatedDate(workspaceCollaboratorDTO.getUpdateddate());
				workspaceCollaborator.setWorkspace(workspace);

				userWorkspaceCollaboratorMap.put(userName, workspaceCollaborator);

			}
		}
		return userWorkspaceCollaboratorMap;
	}



}
