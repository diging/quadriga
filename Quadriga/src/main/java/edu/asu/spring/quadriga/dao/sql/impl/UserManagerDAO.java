package edu.asu.spring.quadriga.dao.sql.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.NotYetImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.asu.spring.quadriga.dao.sql.DAOConnectionManager;
import edu.asu.spring.quadriga.dao.sql.IUserManagerDAO;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.dto.QuadrigaUserDTO;
import edu.asu.spring.quadriga.dto.QuadrigaUserRequestsDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.mapper.UserDTOMapper;

@Repository
public class UserManagerDAO extends DAOConnectionManager implements IUserManagerDAO
{

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private UserDTOMapper userMapper;

	private static final Logger logger = LoggerFactory.getLogger(UserManagerDAO.class);

	@Override
	public IUser getUserDetails(String userid) throws QuadrigaStorageException
	{
		try
		{
			QuadrigaUserDTO userDTO = (QuadrigaUserDTO) sessionFactory.getCurrentSession().get(QuadrigaUserDTO.class,userid);
			if(userDTO == null)
				return null;
			else
				return userMapper.getUser(userDTO);
		}		
		catch(Exception e)
		{
			logger.error("Error in fetching a user detail: ",e);
			throw new QuadrigaStorageException(e);
		}
	}

	@Override
	public List<IUser> getUsers(String userRoleId) throws QuadrigaStorageException
	{
		try
		{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("QuadrigaUserDTO.findByQuadrigarole");
			query.setParameter("quadrigarole", userRoleId);

			List<QuadrigaUserDTO> usersDTO = query.list();
			List<IUser> listUsers = userMapper.getUsers(usersDTO);

			return listUsers;
		}
		catch(Exception e)
		{
			logger.error("Error in adding an account request: ",e);
			throw new QuadrigaStorageException(e);
		}
	}

	@Override
	public int addAccountRequest(String userName) throws QuadrigaStorageException
	{
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

	@Override
	public List<IUser> getUserRequests() throws QuadrigaStorageException
	{
		try
		{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("QuadrigaUserRequestsDTO.findAll");
			List<QuadrigaUserRequestsDTO> userRequestsDTO = query.list();
			List<IUser> listUsers = userMapper.getUserReqests(userRequestsDTO);
			return listUsers;
		}
		catch(Exception e)
		{
			logger.error("Error in fetching all user account requests: ",e);
			throw new QuadrigaStorageException(e);
		}
	}

	@Override
	public List<IUser> getUsersNotInRole(String userRoleId) throws QuadrigaStorageException
	{
		try
		{
			Query query = sessionFactory.getCurrentSession().createQuery(" from QuadrigaUserDTO user where user.quadrigarole not like :quadrigarole");
			query.setParameter("quadrigarole", "'%"+userRoleId+"%'");

			List<QuadrigaUserDTO> usersDTO = query.list();
			List<IUser> listUsers = userMapper.getUsers(usersDTO);

			return listUsers;
		}
		catch(Exception e)
		{
			logger.error("Error in adding an account request: ",e);
			throw new QuadrigaStorageException(e);
		}
	}

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

				sessionFactory.getCurrentSession().delete(userRequestDTO);
				sessionFactory.getCurrentSession().save(userDTO);

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

	@Override
	public int denyUserRequest(String sUserId,String sAdminId) throws QuadrigaStorageException
	{
		try
		{
			
		}
		catch(Exception e)
		{
			logger.error("Error in deactivating user account: ",e);
			throw new QuadrigaStorageException(e);
		}
		throw new NotYetImplementedException("Not yet implememted the method");
	}
	
	@Override
	public void deleteUser(String deleteUser,String adminUser,String adminRole,String deactivatedRole) throws QuadrigaStorageException
	{
		throw new NotYetImplementedException("Not yet implememted the method");
	}
}
