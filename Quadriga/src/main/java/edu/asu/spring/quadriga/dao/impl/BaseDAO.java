package edu.asu.spring.quadriga.dao.impl;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import edu.asu.spring.quadriga.dto.QuadrigaUserDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

/**
 * This class contains the common methods used in 
 * data access object classes.
 * @author kbatna
 */
public abstract class BaseDAO {

	public final static int SUCCESS = 1;
	public final static int FAILURE = 0;

	@Autowired
	protected SessionFactory sessionFactory;
	
	private static final Logger logger = LoggerFactory.getLogger(BaseDAO.class);
	
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
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createSQLQuery(sQuery);
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
	 * Establishes the test environment
	 * @param sQuery
	 * @throws QuadrigaStorageException
	 * @author Karthik Jayaraman
	 */
	public int setupTestEnvironment(String[] sQuery) throws QuadrigaStorageException
	{
		try
		{
			if(sQuery != null && sQuery.length > 0)
			{
				for(String query : sQuery)
				{
					Query sqlQuery = sessionFactory.getCurrentSession().createSQLQuery(query);
					sqlQuery.executeUpdate();
				}
			}
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
	public String generateUniqueID()
	{
			return UUID.randomUUID().toString();
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
		QuadrigaUserDTO quadrigaUser = null;
		try
		{
			quadrigaUser = (QuadrigaUserDTO) sessionFactory.getCurrentSession().get(QuadrigaUserDTO.class, userName);
		}
		catch(HibernateException e)
		{
			logger.error("retrieving Quadriga user DTO :",e);
        	throw new QuadrigaStorageException();
		}
		return quadrigaUser;
	}
	
	/**
	 * This methods splits the comma seperated string into a list
	 * @param users
	 * @return ArrayList<String>
	 */
	public List<String> getList(String commaSeparatedList)
	{
		return Arrays.asList(commaSeparatedList.split(","));
	}


}
