package edu.asu.spring.quadriga.service.workspace.mapper.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.db.workspace.IDBConnectionListWSManager;
import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.ICollaboratorRole;
import edu.asu.spring.quadriga.domain.dspace.IBitStream;
import edu.asu.spring.quadriga.domain.factories.IBitStreamFactory;
import edu.asu.spring.quadriga.domain.factories.ICollaboratorFactory;
import edu.asu.spring.quadriga.domain.factories.ICollaboratorRoleFactory;
import edu.asu.spring.quadriga.domain.factory.workspace.IWorkspaceBitstreamFactory;
import edu.asu.spring.quadriga.domain.factory.workspace.IWorkspaceCollaboratorFactory;
import edu.asu.spring.quadriga.domain.factory.workspace.IWorkspaceFactory;
import edu.asu.spring.quadriga.domain.impl.dspace.BitStream;
import edu.asu.spring.quadriga.domain.workspace.IWorkSpace;
import edu.asu.spring.quadriga.domain.workspace.IWorkspaceBitStream;
import edu.asu.spring.quadriga.domain.workspace.IWorkspaceCollaborator;
import edu.asu.spring.quadriga.dto.WorkspaceCollaboratorDTO;
import edu.asu.spring.quadriga.dto.WorkspaceDTO;
import edu.asu.spring.quadriga.dto.WorkspaceDspaceDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.ICollaboratorRoleManager;
import edu.asu.spring.quadriga.service.IUserManager;
import edu.asu.spring.quadriga.service.workbench.mapper.IProjectShallowMapper;
import edu.asu.spring.quadriga.service.workspace.IListWSManager;
import edu.asu.spring.quadriga.service.workspace.mapper.IWorkspaceCCShallowMapper;
import edu.asu.spring.quadriga.service.workspace.mapper.IWorkspaceDeepMapper;
import edu.asu.spring.quadriga.service.workspace.mapper.IWorkspaceDictionaryShallowMapper;

/**
 * This class would help in mapping {@link IWorkSpace} object with all the variables in it using {@link WorkspaceDTO}.
 * @author Lohith Dwaraka
 *
 */
@Service
public class WorkspaceDeepMapper implements IWorkspaceDeepMapper  {

	@Autowired
	private IDBConnectionListWSManager dbConnect;
	
	@Autowired
	private IWorkspaceCCShallowMapper workspaceCCShallowMapper;
	
	@Autowired
	private IWorkspaceDictionaryShallowMapper workspaceDictionaryShallowMapper;
	
	@Autowired
	private IWorkspaceBitstreamFactory workspaceBitstreamFactory;
	
	@Autowired
	private IBitStreamFactory bitStreamFactory;
	
	@Autowired
	private IWorkspaceFactory workspaceFactory;
	
	@Autowired
	private IWorkspaceCollaboratorFactory workspaceCollaboratorFactory;
	
	@Autowired
	private ICollaboratorRoleManager roleMapper;
	
	@Autowired
	private IListWSManager wsManager;
	
	@Autowired
	private IProjectShallowMapper projectShallowMapper;
	
	@Autowired
	private ICollaboratorFactory collaboratorFactory;
	
	@Autowired
	private ICollaboratorRoleFactory collaboratorRoleFactory;
	
	@Autowired
	private ICollaboratorRoleManager collaboratorRoleManager;
	
	@Autowired
	private IUserManager userManager;
	
	
	/**
	 * {@inheritDoc}
	*/
	@Override
	public IWorkSpace getWorkSpaceDetails(String workspaceId) throws QuadrigaStorageException{

		WorkspaceDTO workspaceDTO = dbConnect.getWorkspaceDTO(workspaceId);
		IWorkSpace workspace = null;
		
		if(workspaceDTO != null){
			workspace = workspaceFactory.createWorkspaceObject();
			workspace.setWorkspaceId(workspaceDTO.getWorkspaceid());
			workspace.setWorkspaceName(workspaceDTO.getWorkspacename());
			workspace.setDescription(workspaceDTO.getDescription());
			workspace.setOwner(userManager.getUserDetails(workspaceDTO.getWorkspaceowner().getUsername()));
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
			
			// TODO : there is a bug in WorkspaceDTO, it returns a List of ProjectWorkspaceDTO. W
			// We need only one ProjectWorkspaceDTO object
			//workspaceProxy.setProject();
			
			workspace.setWorkspaceBitStreams(getWorkspaceBitstream(workspaceDTO, workspace));
		}
				
		return workspace;
		
	}
	
	
	/**
	 * {@inheritDoc}
	*/
	@Override
	public IWorkSpace getWorkSpaceDetails(String workspaceId,String userName) throws QuadrigaStorageException{

		WorkspaceDTO workspaceDTO = dbConnect.getWorkspaceDTO(workspaceId);
		IWorkSpace workspace = null;
		
		if(workspaceDTO != null){
			workspace = workspaceFactory.createWorkspaceObject();
			workspace.setWorkspaceId(workspaceDTO.getWorkspaceid());
			workspace.setWorkspaceName(workspaceDTO.getWorkspacename());
			workspace.setDescription(workspaceDTO.getDescription());
			workspace.setOwner(userManager.getUserDetails(workspaceDTO.getWorkspaceowner().getUsername()));
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
			
			// TODO : there is a bug in WorkspaceDTO, it returns a List of ProjectWorkspaceDTO. W
			// We need only one ProjectWorkspaceDTO object
			//workspaceProxy.setProject();
			
			workspace.setWorkspaceBitStreams(getWorkspaceBitstream(workspaceDTO, workspace));
		}
				
		return workspace;
		
	}
	
	/**
	 * This class should returns a {@link List} of {@link IWorkspaceCollaborator} object by mapping workspaceDTO details and {@link List} of {@link WorkspaceCollaboratorDTO}
	 * @param workspaceDTO										{@link WorkspaceDTO} object of a workspace
	 * @param workspace											{@link IWorkspace} object to refer workspace object in List of {@link IWorkspaceCollaborator}
	 * @return													Returns a {@link List} of {@link IWorkspaceCollaborator} objects
	 * @throws QuadrigaStorageException							Throws a storage exception when method has issue to fetch data from database
	 */
	public List<IWorkspaceCollaborator> getWorkspaceCollaboratorList(WorkspaceDTO workspaceDTO,IWorkSpace workspace) throws QuadrigaStorageException
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
	 */
	public HashMap<String,IWorkspaceCollaborator> mapUserWorkspaceCollaborator(WorkspaceDTO workspaceDTO,IWorkSpace workspace)
	{		
		
		HashMap<String, IWorkspaceCollaborator> userWorkspaceCollaboratorMap = new HashMap<String, IWorkspaceCollaborator>();
		
		for(WorkspaceCollaboratorDTO workspaceCollaboratorDTO : workspaceDTO.getWorkspaceCollaboratorDTOList())
		{
			String userName = workspaceCollaboratorDTO.getQuadrigaUserDTO().getUsername();
			
			if(userWorkspaceCollaboratorMap.containsKey(userName))
			{
				String roleName = workspaceCollaboratorDTO.getWorkspaceCollaboratorDTOPK().getCollaboratorrole();
				
				ICollaboratorRole collaboratorRole = collaboratorRoleFactory.createCollaboratorRoleObject();
				collaboratorRole.setRoleDBid(roleName);
				collaboratorRole.setDisplayName(collaboratorRoleManager.getProjectCollaboratorRoleByDBId(roleName));
				roleMapper.fillProjectCollaboratorRole(collaboratorRole);
				
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
				String roleName = workspaceCollaboratorDTO.getWorkspaceCollaboratorDTOPK().getCollaboratorrole();
				// Prepare collaborator roles
				ICollaboratorRole collaboratorRole = collaboratorRoleFactory.createCollaboratorRoleObject();
				collaboratorRole.setRoleDBid(roleName);
				collaboratorRole.setDisplayName(collaboratorRoleManager.getProjectCollaboratorRoleByDBId(roleName));
				roleMapper.fillProjectCollaboratorRole(collaboratorRole);
				// Create a Collaborator Role list
				List<ICollaboratorRole> collaboratorRoleList = new ArrayList<ICollaboratorRole>();
				// Add collaborator role to the list
				collaboratorRoleList.add(collaboratorRole);
				// Create a Collaborator
				ICollaborator collaborator = collaboratorFactory.createCollaborator();
				// Set Collaborator Role List to the Collaborator
				collaborator.setCollaboratorRoles(collaboratorRoleList);
				
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
	
	
	public List<IWorkspaceBitStream> getWorkspaceBitstream(WorkspaceDTO workspaceDTO , IWorkSpace workspace){
		List<IWorkspaceBitStream> workspaceBitstreamList = null;
		
		List<WorkspaceDspaceDTO> workspaceDspaceDTOList =  workspaceDTO.getWorkspaceDspaceDTOList();
		for(WorkspaceDspaceDTO workspaceDspaceDTO : workspaceDspaceDTOList){
			IBitStream bitStream = getBitstream(workspaceDspaceDTO);
			IWorkspaceBitStream workspaceBitStream =  workspaceBitstreamFactory.createWorkspaceBitstreamObject();
			workspaceBitStream.setBitStream(bitStream);
			workspaceBitStream.setWorkspace(workspace);
			workspaceBitStream.setCreatedBy(workspaceDspaceDTO.getCreatedby());
			workspaceBitStream.setCreatedDate(workspaceDspaceDTO.getCreateddate());
			// TODO : we don;t have updateby and update date in DTO, will do once it is done.
			//workspaceBitStream.setUpdatedBy(workspaceDspaceDTO.)
			//workspaceBitStream.setUpdatedDate(workspaceDspaceDTO.se)
		}
		
		return workspaceBitstreamList;
	}
	
	
	public IBitStream getBitstream(WorkspaceDspaceDTO workspaceDspaceDTO)
	{
		IBitStream bitstream = new BitStream();
		if(workspaceDspaceDTO != null)
		{
			bitstream = bitStreamFactory.createBitStreamObject();					
			bitstream.setId(workspaceDspaceDTO.getWorkspaceDspaceDTOPK().getBitstreamid());
			bitstream.setItemHandle(workspaceDspaceDTO.getWorkspaceDspaceDTOPK().getItemHandle());
		}
		return bitstream;
	}
	


	
}
