package edu.asu.spring.quadriga.dao.workspace;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.asu.spring.quadriga.db.workspace.IDBConnectionRetrieveWSCollabManager;
import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.ICollaboratorRole;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.factories.ICollaboratorFactory;
import edu.asu.spring.quadriga.domain.factories.ICollaboratorRoleFactory;
import edu.asu.spring.quadriga.domain.factories.IUserFactory;
import edu.asu.spring.quadriga.dto.QuadrigaUserDTO;
import edu.asu.spring.quadriga.dto.WorkspaceCollaboratorDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.mapper.UserDTOMapper;
import edu.asu.spring.quadriga.service.ICollaboratorRoleManager;
import edu.asu.spring.quadriga.service.IUserManager;

@Repository
public class RetrieveWSCollabManagerDAO implements IDBConnectionRetrieveWSCollabManager {

	@Autowired
	SessionFactory sessionFactory;
	
	@Autowired
	private ICollaboratorFactory collaboratorFactory;
	
	@Autowired
    private IUserManager userManager;
	
	@Autowired
	private IUserFactory userFactory;
	
	@Autowired
	private ICollaboratorRoleManager collaboratorRoleManager;
	
	@Autowired
	private ICollaboratorRoleFactory collaboratorRoleFactory;
	
	@Autowired
	private UserDTOMapper userDTOMapper;
	
	private static final Logger logger = LoggerFactory.getLogger(RetrieveWSCollabManagerDAO.class);
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<ICollaborator> getWorkspaceCollaborators(String workspaceId)
			throws QuadrigaStorageException {
		List<ICollaboratorRole> collaboratorRoles = new ArrayList<ICollaboratorRole>();
		List<ICollaborator> wrkspaceCollabList = new ArrayList<ICollaborator>();
		try
		{
			Query query = sessionFactory.getCurrentSession().createQuery("from WorkspaceCollaboratorDTO wrkspaceCollab where wrkspaceCollab.workspaceCollaboratorDTOPK.workspaceid =:id");
			query.setParameter("id", workspaceId);
			List<WorkspaceCollaboratorDTO> wrkCollabList = query.list();
			
			if(wrkCollabList != null && wrkCollabList.size() > 0)
			{
				Iterator<WorkspaceCollaboratorDTO> wrkCollabIterator = wrkCollabList.iterator();
				HashMap<String, String> userRoleMap = new HashMap<String, String>();
				while(wrkCollabIterator.hasNext())
				{
					WorkspaceCollaboratorDTO workCollabDTO = wrkCollabIterator.next();
					if(userRoleMap.containsKey(workCollabDTO.getQuadrigaUserDTO().getUsername()))
					{
						String updatedRoleStr = userRoleMap.get(workCollabDTO.getQuadrigaUserDTO().getUsername()).concat(workCollabDTO.getWorkspaceCollaboratorDTOPK().getCollaboratorrole()+",");
						userRoleMap.put(workCollabDTO.getQuadrigaUserDTO().getUsername(), updatedRoleStr);
					}
					else
					{
						userRoleMap.put(workCollabDTO.getQuadrigaUserDTO().getUsername(),workCollabDTO.getWorkspaceCollaboratorDTOPK().getCollaboratorrole()+",");
					}
				}
				
				Iterator<Entry<String, String>> userRoleMapItr = userRoleMap.entrySet().iterator();
				while(userRoleMapItr.hasNext())
				{
					Map.Entry pairs = (Map.Entry)userRoleMapItr.next();
					ICollaborator collaborator = collaboratorFactory.createCollaborator();
					IUser user = userFactory.createUserObject();
					user = userManager.getUserDetails((String) pairs.getKey());
					collaborator.setUserObj(user);
					String userRoleList = (String) pairs.getValue();
					collaboratorRoles = splitAndgetCollaboratorRolesList(userRoleList.substring(0, userRoleList.length()-1));
					collaborator.setCollaboratorRoles(collaboratorRoles);
					wrkspaceCollabList.add(collaborator);
				}
			}	
		}
		catch(Exception e)
		{
			logger.info("getCollaboratedConceptsofUser method :"+e.getMessage());	
			throw new QuadrigaStorageException(e);
		}
		return wrkspaceCollabList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<IUser> getWorkspaceNonCollaborators(String workspaceId) throws QuadrigaStorageException {
		List<IUser> userList = new ArrayList<IUser>();
		try
		{
			Query query = sessionFactory.getCurrentSession().createQuery("from QuadrigaUserDTO user where user.username NOT IN (Select quadrigaUserDTO.username from WorkspaceCollaboratorDTO wrkCollab where wrkCollab.workspaceDTO.workspaceid =:workspaceid)  AND user.username NOT IN (Select ccCollab.conceptcollectionsDTO.collectionowner.username from WorkspaceCollaboratorDTO wrkCollab where wrkCollab.workspaceDTO.workspaceid =:workspaceid)");
			query.setParameter("workspaceid", workspaceId);
			
			List<QuadrigaUserDTO> quadrigaUserDTOList = query.list();
			if(quadrigaUserDTOList != null && quadrigaUserDTOList.size() > 0)
			{
				userList = userDTOMapper.getUsers(quadrigaUserDTOList);
			}
		}
		catch(Exception e)
		{
			logger.info("getWorkspaceNonCollaborators method :"+e.getMessage());	
			throw new QuadrigaStorageException(e);
		}
		return userList;
	}

	@Override
	public List<ICollaboratorRole> getCollaboratorDBRoleIdList(
			String collabRoles) {
		String[] roleList;
		List<ICollaboratorRole> collaboratorRole;
		ICollaboratorRole role;
		
		collaboratorRole = new ArrayList<ICollaboratorRole>();
		roleList = collabRoles.split(",");
		
		for(String dbRoleId : roleList)
		{
			role = collaboratorRoleFactory.createCollaboratorRoleObject();
			role.setRoleDBid(dbRoleId);
			collaboratorRole.add(role);
		}
		return collaboratorRole;
	}

	/**
	 * splits the comma seperated roles string coming from database and converts
	 * into list of roles
	 * 
	 * @param role
	 * @throws QuadrigaStorageException
	 * @author Karthik Jayaraman
	 * 
	 */
	public List<ICollaboratorRole> splitAndgetCollaboratorRolesList(String role) {
		if(role == null || role.isEmpty())
		{
			logger.error("splitAndgetCollaboratorRolesList: input argument role is null");
			return null;
		}
		String[] collabroles;
		List<ICollaboratorRole> collaboratorRoleList = new ArrayList<ICollaboratorRole>();
		ICollaboratorRole collaboratorRole = null;

		collabroles = role.split(",");

		for (int i = 0; i < collabroles.length; i++) {
			collaboratorRole = collaboratorRoleManager.getCollectionCollabRoleByDBId(collabroles[i]);
			collaboratorRoleList.add(collaboratorRole);
		}

		return collaboratorRoleList;
	}
	
}
