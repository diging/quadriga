package edu.asu.spring.quadriga.service.user.mapper.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.spring.quadriga.db.IDBConnectionManager;
import edu.asu.spring.quadriga.domain.IQuadrigaRole;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.factories.IUserFactory;
import edu.asu.spring.quadriga.dto.QuadrigaUserDTO;
import edu.asu.spring.quadriga.dto.QuadrigaUserRequestsDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IQuadrigaRoleManager;
import edu.asu.spring.quadriga.service.user.mapper.IUserDeepMapper;
import edu.asu.spring.quadriga.web.login.RoleNames;

@Service
public class UserDeepMapper implements IUserDeepMapper {

	
	@Autowired
	private IDBConnectionManager dbConnect;
	
	@Autowired
	private IUserFactory userFactory;
	
	@Autowired
	private IQuadrigaRoleManager roleManager;
	
	/**
	 * {@inheritDoc}
	*/
	@Override
	@Transactional
	public IUser getUserDetails(String userName) throws QuadrigaStorageException{
		IUser user = null;
		QuadrigaUserDTO userDTO = dbConnect.getUserDTO(userName);
		List<IQuadrigaRole> userRole = null;
		IQuadrigaRole quadrigaRole = null;
		List<IQuadrigaRole> rolesList = new ArrayList<IQuadrigaRole>();
		
		if(userDTO != null)
		{
			user = userFactory.createUserObject();
			user.setUserName(userDTO.getUsername());
			user.setName(userDTO.getFullname());
			user.setEmail(userDTO.getEmail());
			user.setPassword(userDTO.getPasswd());
			user.setQuadrigaRoles(listQuadrigaUserRoles(userDTO.getQuadrigarole()));
		}
		if(user!=null)
		{
			userRole = user.getQuadrigaRoles();

			for(int i=0;i<userRole.size();i++)
			{
				quadrigaRole = roleManager.getQuadrigaRole(userRole.get(i).getDBid());

				//If user account is deactivated remove other roles 
				if(quadrigaRole.getId().equals(RoleNames.ROLE_QUADRIGA_DEACTIVATED))
				{
					rolesList.clear();
					rolesList.add(quadrigaRole);
					break;
				}
				rolesList.add(quadrigaRole);
			}
			user.setQuadrigaRoles(rolesList);
		}
		else
		{
			user = userFactory.createUserObject();
			quadrigaRole = roleManager.getQuadrigaRole(RoleNames.DB_ROLE_QUADRIGA_NOACCOUNT);
			rolesList.add(quadrigaRole);
			user.setQuadrigaRoles(rolesList);
		}
		
		return user;
	}
	
	/**
	 * {@inheritDoc}
	*/
	@Override
	@Transactional
	public List<IUser> getAllActiveUsers() throws QuadrigaStorageException
	{
		List<IUser> listUsers = null;

		//Find the ROLEDBID for Deactivated account
		String sDeactiveRoleDBId = roleManager.getQuadrigaRoleDBId(RoleNames.ROLE_QUADRIGA_DEACTIVATED);

		List<QuadrigaUserDTO> usersDTOList  = dbConnect.getUserDTOListNotInRole(sDeactiveRoleDBId);

		listUsers = getUserListFromQuadrigaUsersDTOList(usersDTOList);

		return listUsers;
	}
	
	
	/**
	 * {@inheritDoc}
	*/
	@Override
	@Transactional
	public List<IUser> getAllInActiveUsers() throws QuadrigaStorageException
	{
		List<IUser> listUsers = null;

		//Find the ROLEDBID for Deactivated account
		String sDeactiveRoleDBId = roleManager.getQuadrigaRoleDBId(RoleNames.ROLE_QUADRIGA_DEACTIVATED);

		List<QuadrigaUserDTO> usersDTOList = dbConnect.getUserDTOList(sDeactiveRoleDBId);
		
		listUsers = getUserListFromQuadrigaUsersDTOList(usersDTOList);
		
		return listUsers;		
	}
	
	/**
	 * {@inheritDoc}
	*/
	@Override
	@Transactional
	public List<IUser> getUsersByRoleId(String roleId) throws QuadrigaStorageException{
		List<QuadrigaUserDTO> usersDTOList = dbConnect.getUserDTOList(roleId);
		
		List<IUser> listUsers = getUserListFromQuadrigaUsersDTOList(usersDTOList);
		
		return listUsers;
	}
	
	
	
	/**
	 * {@inheritDoc}
	*/
	@Override
	@Transactional
	public List<IUser> getUserRequests() throws QuadrigaStorageException
	{
		List<IUser> listUsers = null;

		List<QuadrigaUserRequestsDTO> userRequestDTOList = dbConnect.getUserRequestDTOList();
		listUsers = getUserRequestListFromQuadrigaUserRequestDTOList(userRequestDTOList);
		
		return listUsers;		
	}
	
	
	
	
	public List<IUser> getUserListFromQuadrigaUsersDTOList(List<QuadrigaUserDTO> usersDTOList)
	{
		List<IUser> listUsers = null;
		if(usersDTOList != null)
		{
			IUser user = null;
			listUsers = new ArrayList<IUser>();

			for(QuadrigaUserDTO userDTO : usersDTOList)
			{
				user = userFactory.createUserObject();
				user.setUserName(userDTO.getUsername());
				user.setName(userDTO.getFullname());
				user.setEmail(userDTO.getEmail());
				user.setQuadrigaRoles(listQuadrigaUserRoles(userDTO.getQuadrigarole()));
				listUsers.add(user);
			}
		}

		return listUsers;
	}
	
	public List<IUser> getUserRequestListFromQuadrigaUserRequestDTOList(List<QuadrigaUserRequestsDTO> userRequestsDTO)
	{
		List<IUser> listUsers = null;
		if(userRequestsDTO != null)
		{
			IUser user = null;
			listUsers = new ArrayList<IUser>();

			for(QuadrigaUserRequestsDTO userRequestDTO : userRequestsDTO)
			{
				user = userFactory.createUserObject();
				user.setUserName(userRequestDTO.getUsername());
				user.setName(userRequestDTO.getFullname());
				user.setEmail(userRequestDTO.getEmail());
				listUsers.add(user);
			}
		}

		return listUsers;
	}
	
	public List<IQuadrigaRole> listQuadrigaUserRoles(String roles)
	{
		String[] role;
		List<IQuadrigaRole> rolesList = new ArrayList<IQuadrigaRole>();
		IQuadrigaRole userRole = null;

		if(roles != null && roles.length()>0)
		{
			role = roles.split("\\s*,\\s*");
			for(int i = 0; i<role.length;i++)
			{
				userRole = roleManager.getQuadrigaRole(role[i]);
				rolesList.add(userRole);
			}
		}
		return rolesList;
	}
}
