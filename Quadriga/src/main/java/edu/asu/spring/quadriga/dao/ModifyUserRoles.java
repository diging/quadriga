package edu.asu.spring.quadriga.dao;

import java.util.Date;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.asu.spring.quadriga.db.IModifyUserRoles;
import edu.asu.spring.quadriga.dto.QuadrigaUserDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.mapper.UserDTOMapper;

@Repository
public class ModifyUserRoles extends DAOConnectionManager implements IModifyUserRoles
{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private UserDTOMapper userMapper;
	
	private static final Logger logger = LoggerFactory.getLogger(ModifyUserRoles.class);
	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updateUserQuadrigaRoles(String userName,String quadrigaRoles, String loggedInUser) throws QuadrigaStorageException
	{
		//check if the user name is null
		if((userName == null) || (userName.isEmpty()))
		{
			throw new QuadrigaStorageException();
		}
		
		try
		{
		//fetch the user session
		QuadrigaUserDTO userDTO = (QuadrigaUserDTO) sessionFactory.getCurrentSession().get(QuadrigaUserDTO.class,userName);
		userDTO.setQuadrigarole(quadrigaRoles);
		userDTO.setUpdatedby(loggedInUser);
		userDTO.setUpdateddate(new Date());
		sessionFactory.getCurrentSession().save(userDTO);
		}
		catch(Exception ex)
		{
			logger.error("updateUserQuadrigaRoles :",ex);
			throw new QuadrigaStorageException();
		}
		
	}

}
