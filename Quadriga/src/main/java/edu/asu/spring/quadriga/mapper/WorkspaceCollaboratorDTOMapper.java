package edu.asu.spring.quadriga.mapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.dao.workspace.IRetrieveWSCollabDAO;
import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.IQuadrigaRole;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.factories.ICollaboratorFactory;
import edu.asu.spring.quadriga.domain.factories.IUserFactory;
import edu.asu.spring.quadriga.dto.WorkspaceCollaboratorDTO;
import edu.asu.spring.quadriga.dto.WorkspaceCollaboratorDTOPK;
import edu.asu.spring.quadriga.dto.WorkspaceDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IUserManager;

@Service
public class WorkspaceCollaboratorDTOMapper extends BaseMapper {
	@Autowired
	private ICollaboratorFactory collaboratorFactory;
	
	@Autowired
    private IUserManager userManager;
	
	@Autowired
	private IUserFactory userFactory;
	
	@Autowired
	private IRetrieveWSCollabDAO collaboratorManager;
	
	private static final Logger logger = LoggerFactory.getLogger(WorkspaceCollaboratorDTOMapper.class);
	
	public WorkspaceCollaboratorDTO getWorkspaceCollaboratorDTO(WorkspaceDTO workspace,String userName,String collaboratorRole) {
		WorkspaceCollaboratorDTO collaborator = null;
		WorkspaceCollaboratorDTOPK collaboratorKey = null;
		collaboratorKey = new WorkspaceCollaboratorDTOPK(workspace.getWorkspaceid(),userName,collaboratorRole);
		Date date = new Date();
		collaborator = new WorkspaceCollaboratorDTO();
		collaborator.setCollaboratorDTOPK(collaboratorKey);
		collaborator.setWorkspaceDTO(workspace);
		collaborator.setQuadrigaUserDTO(getUserDTO(userName));
		collaborator.setCreatedby(userName);
		collaborator.setCreateddate(date);
		collaborator.setUpdatedby(userName);
		collaborator.setUpdateddate(date);
		return collaborator;
	}
	
	/**
	 * This method returns the 
	 * @param wrkCollabList
	 * @return
	 * @throws QuadrigaStorageException
	 */
	public List<ICollaborator> getWorkspaceCollaborators(List<WorkspaceCollaboratorDTO> wrkCollabList) throws QuadrigaStorageException
	{
		List<IQuadrigaRole> collaboratorRoles = new ArrayList<IQuadrigaRole>();
		List<ICollaborator> wrkspaceCollabList = new ArrayList<ICollaborator>();
		
		if(wrkCollabList != null && wrkCollabList.size() > 0)
		{
			Iterator<WorkspaceCollaboratorDTO> wrkCollabIterator = wrkCollabList.iterator();
			HashMap<String, String> userRoleMap = new HashMap<String, String>();
			while(wrkCollabIterator.hasNext())
			{
				WorkspaceCollaboratorDTO workCollabDTO = wrkCollabIterator.next();
				if(userRoleMap.containsKey(workCollabDTO.getQuadrigaUserDTO().getUsername()))
				{
					String updatedRoleStr = userRoleMap.get(workCollabDTO.getQuadrigaUserDTO().getUsername()).concat(workCollabDTO.getCollaboratorDTOPK().getCollaboratorrole()+",");
					userRoleMap.put(workCollabDTO.getQuadrigaUserDTO().getUsername(), updatedRoleStr);
				}
				else
				{
					userRoleMap.put(workCollabDTO.getQuadrigaUserDTO().getUsername(),workCollabDTO.getCollaboratorDTOPK().getCollaboratorrole()+",");
				}
			}
			
			Iterator<Entry<String, String>> userRoleMapItr = userRoleMap.entrySet().iterator();
			while(userRoleMapItr.hasNext())
			{
				@SuppressWarnings("rawtypes")
				Map.Entry pairs = (Map.Entry)userRoleMapItr.next();
				ICollaborator collaborator = collaboratorFactory.createCollaborator();
				IUser user = userFactory.createUserObject();
				user = userManager.getUser((String) pairs.getKey());
				collaborator.setUserObj(user);
				String userRoleList = (String) pairs.getValue();
				collaboratorRoles = collaboratorManager.getCollaboratorDBRoleIdList(userRoleList.substring(0, userRoleList.length()-1));
				collaborator.setCollaboratorRoles(collaboratorRoles);
				wrkspaceCollabList.add(collaborator);
			}
		}
		
		return wrkspaceCollabList;
	}

}
