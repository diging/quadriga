package edu.asu.spring.quadriga.dao.sql;

import java.util.ArrayList;
import java.util.UUID;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import edu.asu.spring.quadriga.dto.QuadrigaUserDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public abstract class DAOConnectionManager {

	public final static int SUCCESS = 1;
	public final static int FAILURE = 0;

	@Autowired
	protected SessionFactory sessionFactory;
	
	private static final Logger logger = LoggerFactory.getLogger(DAOConnectionManager.class);
	
	/**
	 * Establishes the test environment
	 * @param sQuery
	 * @throws QuadrigaStorageException
	 * @author Karthik Jayaraman
	 */
	public int setupTestEnvironment(String sQuery) throws QuadrigaStorageException
	{
		try
		{
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sQuery);
			query.executeUpdate();
			return SUCCESS;
		}
		catch(Exception ex)
		{	
			ex.printStackTrace();
			throw new QuadrigaStorageException();
		}
	}
	

	/**
	 * Generate an unique identifier for the database field
	 * @throws QuadrigaStorageException
	 * @author Karthik Jayaraman
	 */
	public String generateUniqueID() throws QuadrigaStorageException
	{
		try
		{
			return UUID.randomUUID().toString();
		}
		catch(Exception ex)
		{	
			ex.printStackTrace();
			throw new QuadrigaStorageException();
		}
	}
	
	/**
	 * This method returns the User DAO object for the given userName
	 * @param userName
	 * @return
	 * @throws QuadrigaStorageException
	 * @author Kiran Batna
	 */
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
	
	public ArrayList<String> getList(String users)
	{
		ArrayList<String> usersList = new ArrayList<String>();
		String[] userValues = users.split(",");
		for(String user : userValues)
		{
		   usersList.add(user);	
		}
		return usersList;
	}
}