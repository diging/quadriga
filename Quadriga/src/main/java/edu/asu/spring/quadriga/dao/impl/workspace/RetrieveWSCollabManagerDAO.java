package edu.asu.spring.quadriga.dao.impl.workspace;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.asu.spring.quadriga.dao.impl.BaseDAO;
import edu.asu.spring.quadriga.dao.workspace.IDBConnectionRetrieveWSCollabManager;
import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.IQuadrigaRole;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.factories.ICollaboratorFactory;
import edu.asu.spring.quadriga.domain.factories.IQuadrigaRoleFactory;
import edu.asu.spring.quadriga.domain.factories.IUserFactory;
import edu.asu.spring.quadriga.dto.QuadrigaUserDTO;
import edu.asu.spring.quadriga.dto.WorkspaceCollaboratorDTO;
import edu.asu.spring.quadriga.dto.WorkspaceDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.mapper.UserDTOMapper;
import edu.asu.spring.quadriga.mapper.WorkspaceCollaboratorDTOMapper;
import edu.asu.spring.quadriga.service.IQuadrigaRoleManager;
import edu.asu.spring.quadriga.service.IUserManager;

@Repository
public class RetrieveWSCollabManagerDAO extends BaseDAO implements IDBConnectionRetrieveWSCollabManager {

	@Autowired
	SessionFactory sessionFactory;
	
	@Autowired
	private WorkspaceCollaboratorDTOMapper wsCollaboratorMapper;
	
	@Autowired
	private ICollaboratorFactory collaboratorFactory;
	
	@Autowired
    private IUserManager userManager;
	
	@Autowired
	private IUserFactory userFactory;
	
	@Autowired
	private IQuadrigaRoleManager roleManager;
	
	@Autowired
	private IQuadrigaRoleFactory roleFactory;
	
	@Autowired
	private UserDTOMapper userDTOMapper;
	
	private static final Logger logger = LoggerFactory.getLogger(RetrieveWSCollabManagerDAO.class);
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ICollaborator> getWorkspaceCollaborators(String workspaceId)
			throws QuadrigaStorageException 
	{
		List<ICollaborator> wrkspaceCollabList = new ArrayList<ICollaborator>();
		try
		{
			Query query = sessionFactory.getCurrentSession().createQuery("from WorkspaceCollaboratorDTO wrkspaceCollab where wrkspaceCollab.collaboratorDTOPK.workspaceid =:id");
			query.setParameter("id", workspaceId);
			@SuppressWarnings("unchecked")
			List<WorkspaceCollaboratorDTO> wrkCollabList = query.list();
			wrkspaceCollabList = wsCollaboratorMapper.getWorkspaceCollaborators(wrkCollabList);
		}
		catch(Exception e)
		{
			logger.info("getCollaboratedConceptsofUser method :"+e.getMessage());	
			throw new QuadrigaStorageException(e);
		}
		return wrkspaceCollabList;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<IUser> getWorkspaceNonCollaborators(String workspaceId) throws QuadrigaStorageException {
		List<IUser> userList = new ArrayList<IUser>();
		try
		{
			WorkspaceDTO workspace = (WorkspaceDTO) sessionFactory.getCurrentSession().get(WorkspaceDTO.class, workspaceId);
			
			Query query = sessionFactory.getCurrentSession().createQuery("from QuadrigaUserDTO user where user.username NOT IN (Select quadrigaUserDTO.username from WorkspaceCollaboratorDTO wrkCollab where wrkCollab.workspaceDTO.workspaceid =:workspaceid)");
			query.setParameter("workspaceid", workspaceId);
			
			List<QuadrigaUserDTO> quadrigaUserDTOList = query.list();
			
			//remove the logged in user name
			QuadrigaUserDTO workspaceOwner = workspace.getWorkspaceowner();
			
			if(quadrigaUserDTOList != null)
			{
				quadrigaUserDTOList.remove(workspaceOwner);
			}
			
			if(quadrigaUserDTOList.size() > 0)
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<IQuadrigaRole> getCollaboratorDBRoleIdList(
			String collabRoles) {
		String[] roleList;
		List<IQuadrigaRole> collaboratorRole;
		IQuadrigaRole role;
		
		collaboratorRole = new ArrayList<IQuadrigaRole>();
		roleList = collabRoles.split(",");
		
		for(String dbRoleId : roleList)
		{
			role = roleManager.getQuadrigaRoleByDbId(IQuadrigaRoleManager.WORKSPACE_ROLES, dbRoleId);
			collaboratorRole.add(role);
		}
		return collaboratorRole;
	}
}
