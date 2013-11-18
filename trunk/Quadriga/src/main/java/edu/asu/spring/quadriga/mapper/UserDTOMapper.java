package edu.asu.spring.quadriga.mapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.IQuadrigaRole;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.factories.IQuadrigaRoleFactory;
import edu.asu.spring.quadriga.domain.factories.impl.UserFactory;
import edu.asu.spring.quadriga.dto.QuadrigaUserDTO;
import edu.asu.spring.quadriga.dto.QuadrigaUserRequestsDTO;


@Service
public class UserDTOMapper{

	@Autowired
    private UserFactory userFactory;	
	
	@Autowired
	private IQuadrigaRoleFactory quadrigaRoleFactory;
	
	public IUser getUser(QuadrigaUserDTO userDTO)
	{
		IUser user = userFactory.createUserObject();
		user.setUserName(userDTO.getUsername());
		user.setName(userDTO.getFullname());
		user.setEmail(userDTO.getEmail());
		user.setPassword(userDTO.getPasswd());
		user.setQuadrigaRoles(listQuadrigaUserRoles(userDTO.getQuadrigarole()));
		
		return user;
	}
	
	public QuadrigaUserRequestsDTO getUserRequestDTO(String username)
	{
		QuadrigaUserRequestsDTO userRequestDTO = new QuadrigaUserRequestsDTO();
		userRequestDTO.setUsername(username);
		userRequestDTO.setCreatedby(username);
		userRequestDTO.setCreateddate(new Date());
		userRequestDTO.setUpdatedby(username);
		userRequestDTO.setUpdateddate(new Date());		
		
		return userRequestDTO;
	}
	
	public List<IUser> getUserReqests(List<QuadrigaUserRequestsDTO> userRequestsDTO)
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
	
	public List<IUser> getUsers(List<QuadrigaUserDTO> usersDTO)
	{
		List<IUser> listUsers = null;
		if(usersDTO != null)
		{
			IUser user = null;
			listUsers = new ArrayList<IUser>();
			
			for(QuadrigaUserDTO userDTO : usersDTO)
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
	
	/**
	 *   @Description   : Splits the comma separated roles into a list
	 *   
	 *   @param         : roles - String containing the comma separated roles
	 *                            (ex: role1,role3)
	 *                            
	 *   @return        : list of QuadrigaRoles.
	 */
	public List<IQuadrigaRole> listQuadrigaUserRoles(String roles)
	{
		String[] role;
		List<IQuadrigaRole> rolesList = new ArrayList<IQuadrigaRole>();
		IQuadrigaRole userRole = null;

		role = roles.split("\\s*,\\s*");
		for(int i = 0; i<role.length;i++)
		{
			userRole = quadrigaRoleFactory.createQuadrigaRoleObject();
			userRole.setDBid(role[i]);
			rolesList.add(userRole);
		}
		return rolesList;
	}
}
