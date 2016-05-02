package edu.asu.spring.quadriga.mapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.IQuadrigaRole;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.factories.impl.UserFactory;
import edu.asu.spring.quadriga.dto.QuadrigaUserDTO;
import edu.asu.spring.quadriga.dto.QuadrigaUserRequestsDTO;
import edu.asu.spring.quadriga.service.IQuadrigaRoleManager;

/**
 * Class responsible for mapping the UserManagerDTO to the UserManager
 * java class
 * 
 * @author Ram Kumar Kumaresan
 *
 */
@Service
public class UserDTOMapper extends BaseMapper {

	@Autowired
	private UserFactory userFactory;	

	@Autowired
	private IQuadrigaRoleManager roleManager;
	
	/**
	 * Convert a userDTO object to a user object of java class. 
	 * If the object is null, a null object will be returned.
	 * 
	 * @param userDTO	The QuadrigaUserDTO object which needs to be converted to User object
	 * @return	A user object of the Java User domain class
	 */
	public IUser getUser(QuadrigaUserDTO userDTO)
	{
	    if (userDTO == null) {
	        return null;
	    }
	    
		IUser user = userFactory.createUserObject();
		user.setUserName(userDTO.getUsername());
		user.setName(userDTO.getFullname());
		user.setEmail(userDTO.getEmail());
		user.setPassword(userDTO.getPasswd());
		user.setQuadrigaRoles(listQuadrigaUserRoles(userDTO.getQuadrigarole()));	
		return user;
	}

	/**
	 * Create a QuadrigaUserRequestsDTO from a username. The username, createdby, createddate, updatedby, updateddate
	 * will be set in the returned object. 
	 *  
	 * @param username	The username of the user for which the request dto need to be created.
	 * @return	A QuadrigaUserRequestsDTO object created based on the input username
	 */
	public QuadrigaUserRequestsDTO getUserRequestDTO(String username)
	{
		QuadrigaUserRequestsDTO userRequestDTO = null;
		if(username != null)
		{
			userRequestDTO = new QuadrigaUserRequestsDTO();
			userRequestDTO.setUsername(username);
			userRequestDTO.setFullname(username);
			userRequestDTO.setCreatedby(username);
			userRequestDTO.setCreateddate(new Date());
			userRequestDTO.setUpdatedby(username);
			userRequestDTO.setUpdateddate(new Date());		
		}
		return userRequestDTO;
	}

	/**
	 * Create a list of users from the list of quadriga user request objects.
	 * Null will be returned if the input list is null.
	 * 
	 * @param userRequestsDTO		The list of user requests fetched from database
	 * @return	The list of user objects created from the input list
	 */
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

	/**
	 * Convert a list of QuadrigaUserDTO into a list of User objects.
	 * Null will be returned if the input list is null.
	 * 
	 * @param usersDTO		List of usersDTO that need to be converted to User objects
	 * @return		List of user objects created from the input list
	 */
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
		List<IQuadrigaRole> rolesList = new ArrayList<IQuadrigaRole>();
		IQuadrigaRole userRole = null;

		if(roles != null && roles.length()>0)
		{
		    String[] role = roles.split("\\s*,\\s*");
			for(int i = 0; i<role.length;i++)
			{
				userRole = roleManager.getQuadrigaRoleByDbId(IQuadrigaRoleManager.MAIN_ROLES, role[i]);
				rolesList.add(userRole);
			}
		}
		return rolesList;
	}
}
