package edu.asu.spring.quadriga.dao;

import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.asu.spring.quadriga.db.IDBConnectionManager;
import edu.asu.spring.quadriga.dto.QuadrigaUserDTO;
import edu.asu.spring.quadriga.dto.QuadrigaUserDeniedDTO;
import edu.asu.spring.quadriga.dto.QuadrigaUserRequestsDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.mapper.UserDTOMapper;

/**
 * This class is responsible for Querying the MySQL database
 * and fetch the class objects related to User module
 * 
 * @author Ram Kumar Kumaresan
 *
 */
@Repository
public class UserManagerDAO extends DAOConnectionManager implements IDBConnectionManager
{

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private UserDTOMapper userMapper;

	@Resource(name = "database_error_msgs")
	private Properties messages;

	private static final Logger logger = LoggerFactory.getLogger(UserManagerDAO.class);




	/**
	 * {@inheritDoc} 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<QuadrigaUserDTO> getUserDTOList(String userRoleId) throws QuadrigaStorageException
	{
		List<QuadrigaUserDTO> usersDTOList = null;
		try
		{
			Query query = sessionFactory.getCurrentSession().createQuery(" from QuadrigaUserDTO user where user.quadrigarole like :quadrigarole");
			query.setParameter("quadrigarole", "%"+userRoleId+"%");

			usersDTOList = query.list();

			return usersDTOList;
		}
		catch(Exception e)
		{
			logger.error("Error in adding an account request: ",e);
			throw new QuadrigaStorageException(e);
		}
	}
	
	/**
	 * {@inheritDoc} 
	 */
	@Override
	public int addAccountRequest(String userName) throws QuadrigaStorageException
	{
		if(userName == null || userName.equals(""))
			return FAILURE;

		QuadrigaUserRequestsDTO userRequestDTO = userMapper.getUserRequestDTO(userName);
		try
		{
			sessionFactory.getCurrentSession().save(userRequestDTO);	
			return SUCCESS;
		}
		catch(Exception e)
		{
			logger.error("Error in adding an account request: ",e);
			throw new QuadrigaStorageException(e);
		}		
	}
	
	
	/**
	 * {@inheritDoc} 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<QuadrigaUserRequestsDTO> getUserRequestDTOList() throws QuadrigaStorageException
	{
		List<QuadrigaUserRequestsDTO> userRequestDTOList = null;
		try
		{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("QuadrigaUserRequestsDTO.findAll");
			userRequestDTOList = query.list();

			return userRequestDTOList;
		}
		catch(Exception e)
		{
			logger.error("Error in fetching all user account requests: ",e);
			throw new QuadrigaStorageException(e);
		}
	}

	
	
	
	/**
	 * {@inheritDoc} 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<QuadrigaUserDTO> getUserDTOListNotInRole(String userRoleId) throws QuadrigaStorageException
	{

		List<QuadrigaUserDTO> usersDTOList = null;
		try
		{
			Query query = sessionFactory.getCurrentSession().createQuery(" from QuadrigaUserDTO user where user.quadrigarole not like :quadrigarole");
			query.setParameter("quadrigarole", "%"+userRoleId+"%");
			usersDTOList = query.list();

			return usersDTOList;
		}
		catch(Exception e)
		{
			logger.error("Error in adding an account request: ",e);
			throw new QuadrigaStorageException(e);
		}
	}
	

	/**
	 * {@inheritDoc} 
	 */
	@Override
	public int deactivateUser(String sUserId,String sDeactiveRoleDBId,String sAdminId) throws QuadrigaStorageException
	{
		try
		{
			QuadrigaUserDTO userDTO = (QuadrigaUserDTO) sessionFactory.getCurrentSession().get(QuadrigaUserDTO.class,sUserId);

			if(userDTO != null)
			{
				if(userDTO.getQuadrigarole().length() == 0)
					userDTO.setQuadrigarole(sDeactiveRoleDBId);
				else
					userDTO.setQuadrigarole(userDTO.getQuadrigarole() + ","+sDeactiveRoleDBId);

				userDTO.setUpdatedby(sAdminId);
				userDTO.setUpdateddate(new Date());

				sessionFactory.getCurrentSession().update(userDTO);

				return SUCCESS;
			}
			else
				return FAILURE;
		}
		catch(Exception e)
		{
			logger.error("Error in deactivating user account: ",e);
			throw new QuadrigaStorageException(e);
		}		
	}

	/**
	 * {@inheritDoc} 
	 */
	@Override
	public int updateUserRoles(String sUserId,String sRoles,String sAdminId) throws QuadrigaStorageException
	{
		try
		{
			QuadrigaUserDTO userDTO = (QuadrigaUserDTO) sessionFactory.getCurrentSession().get(QuadrigaUserDTO.class,sUserId);

			if(userDTO != null)
			{
				userDTO.setQuadrigarole(sRoles);
				userDTO.setUpdatedby(sAdminId);
				userDTO.setUpdateddate(new Date());
				sessionFactory.getCurrentSession().update(userDTO);

				return SUCCESS;
			}
			else
				return FAILURE;
		}
		catch(Exception e)
		{
			logger.error("Error in deactivating user account: ",e);
			throw new QuadrigaStorageException(e);
		}
	}

	/**
	 * {@inheritDoc} 
	 */
	@Override
	public int approveUserRequest(String sUserId, String sRoles, String sAdminId) throws QuadrigaStorageException
	{		
		try
		{
			QuadrigaUserRequestsDTO userRequestDTO = (QuadrigaUserRequestsDTO) sessionFactory.getCurrentSession().get(QuadrigaUserRequestsDTO.class,sUserId);

			if(userRequestDTO != null)
			{
				QuadrigaUserDTO userDTO = new QuadrigaUserDTO();
				userDTO.setFullname(userRequestDTO.getFullname());
				userDTO.setUsername(userRequestDTO.getUsername());
				userDTO.setPasswd(userRequestDTO.getPasswd());
				userDTO.setEmail(userRequestDTO.getEmail());
				userDTO.setQuadrigarole(sRoles);
				userDTO.setCreatedby(sAdminId);
				userDTO.setCreateddate(new Date());
				userDTO.setUpdatedby(sAdminId);
				userDTO.setUpdateddate(new Date());

				Session session = sessionFactory.getCurrentSession();
				session.save(userDTO);
				session.delete(userRequestDTO);

				return SUCCESS;
			}
			else
				return FAILURE;
		}
		catch(Exception e)
		{
			logger.error("Error in deactivating user account: ",e);
			throw new QuadrigaStorageException(e);
		}
	}

	/**
	 * {@inheritDoc} 
	 */
	@Override
	public int denyUserRequest(String sUserId,String sAdminId) throws QuadrigaStorageException
	{
		try
		{
			QuadrigaUserRequestsDTO userRequestDTO = (QuadrigaUserRequestsDTO) sessionFactory.getCurrentSession().get(QuadrigaUserRequestsDTO.class,sUserId);

			if(userRequestDTO != null)
			{
				QuadrigaUserDeniedDTO userDeniedDTO = new QuadrigaUserDeniedDTO();
				userDeniedDTO.setActionid(generateUniqueID());
				userDeniedDTO.setFullname(userRequestDTO.getFullname());
				userDeniedDTO.setUsername(userRequestDTO.getUsername());
				userDeniedDTO.setPasswd(userRequestDTO.getPasswd());
				userDeniedDTO.setEmail(userRequestDTO.getEmail());
				userDeniedDTO.setDeniedby(sAdminId);
				userDeniedDTO.setUpdatedby(sAdminId);
				userDeniedDTO.setUpdateddate(new Date());
				userDeniedDTO.setCreatedby(sAdminId);
				userDeniedDTO.setCreateddate(new Date());

				Session session = sessionFactory.getCurrentSession();
				session.save(userDeniedDTO);
				session.delete(userRequestDTO);				

				return SUCCESS;
			}
			else
				return FAILURE;

		}
		catch(Exception e)
		{
			logger.error("Error in deactivating user account: ",e);
			throw new QuadrigaStorageException(e);
		}
	}

	/**
	 * {@inheritDoc} 
	 */
	@Override
	public int deleteUser(String deleteUser, String deactivatedRole) throws QuadrigaStorageException
	{
		try
		{
			QuadrigaUserDTO userDTO = (QuadrigaUserDTO) sessionFactory.getCurrentSession().get(QuadrigaUserDTO.class,deleteUser);

			if(userDTO != null)
			{
				if(!userDTO.getQuadrigarole().contains(deactivatedRole))
					throw new QuadrigaStorageException(messages.getProperty("delete_user_not_deactivated")); 

				if(userDTO.getProjectDTOList().size() != 0)
				{
					throw new QuadrigaStorageException(messages.getProperty("delete_user_project_owner"));
				}
				else if(userDTO.getWorkspaceDTOList().size() != 0)
				{
					throw new QuadrigaStorageException(messages.getProperty("delete_user_workspace_owner"));
				}
				else if(userDTO.getProjectCollaboratorDTOList().size() != 0)
				{
					throw new QuadrigaStorageException(messages.getProperty("delete_user_project_collaborator"));
				}
				else if(userDTO.getWorkspaceCollaboratorDTOList().size() != 0)
				{
					throw new QuadrigaStorageException(messages.getProperty("delete_user_workspace_collaborator"));
				}
				else
				{
					sessionFactory.getCurrentSession().delete(userDTO);
					return SUCCESS;
				}
			}
			else
				return FAILURE;
		}
		catch(Exception e)
		{
			logger.error("Error in deactivating user account: ",e);
			throw new QuadrigaStorageException(e);
		}
	}
	
	/**
	 * This method returns the User DAO object for the given userName
	 * @param userName
	 * @return
	 * @throws QuadrigaStorageException
	 * @author Kiran Batna
	 */
	@Override
	public QuadrigaUserDTO getUserDTO(String userName) throws QuadrigaStorageException
	{
		try
		{
		Query query = sessionFactory.getCurrentSession().getNamedQuery("QuadrigaUserDTO.findByUsername");
		query.setParameter("username", userName);
		return (QuadrigaUserDTO) query.uniqueResult();
		}
		catch(Exception e)
		{
			logger.error("getProjectOwner :",e);
        	throw new QuadrigaStorageException();
		}
	}
	
	
	/**
	 * This method inserts the quadiriga Admin user record into the daabase
	 * @param userName - Quadriga admin user name
	 * @param sRoles - quadriga Roles possed by the admin
	 * @throws QuadrigaStorageException - represents any database exception
	 * @author kiran batna
	 */
	@Override
	public void insertQuadrigaAdminUser(String userName, String sRoles) throws QuadrigaStorageException
	{
		try
		{
			QuadrigaUserDTO user = (QuadrigaUserDTO) sessionFactory.getCurrentSession().get(QuadrigaUserDTO.class, userName);
			if(user == null)
			{
				//insert the record into the quadriga user table
				Date date = new Date();
				QuadrigaUserDTO quadrigaAdmin = new QuadrigaUserDTO();
				quadrigaAdmin.setFullname(userName);
				quadrigaAdmin.setUsername(userName);
				quadrigaAdmin.setPasswd(null);
				quadrigaAdmin.setQuadrigarole(sRoles);
				quadrigaAdmin.setCreatedby(userName);
				quadrigaAdmin.setCreateddate(date);
				quadrigaAdmin.setUpdatedby(userName);
				quadrigaAdmin.setUpdateddate(date);
				sessionFactory.getCurrentSession().save(quadrigaAdmin);
				
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			throw new QuadrigaStorageException();
		}
	}
}