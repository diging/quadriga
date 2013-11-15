package edu.asu.spring.quadriga.dao.sql.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.asu.spring.quadriga.dao.sql.DAOConnectionManager;
import edu.asu.spring.quadriga.dao.sql.IUserManagerDAO;
import edu.asu.spring.quadriga.domain.IUser;
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
			logger.error("addAccountRequest :",e);
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
			logger.error("addAccountRequest :",e);
			throw new QuadrigaStorageException(e);
		}
	}
}
