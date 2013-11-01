package edu.asu.spring.quadriga.db.sql;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public abstract class DAOConnectionManager {

	public final static int SUCCESS = 1;
	public final static int FAILURE = 0;

	@Autowired
	protected SessionFactory sessionFactory;

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
}
