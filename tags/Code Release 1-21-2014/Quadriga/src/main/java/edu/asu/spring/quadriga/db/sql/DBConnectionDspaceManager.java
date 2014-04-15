package edu.asu.spring.quadriga.db.sql;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.springframework.beans.factory.annotation.Autowired;

import edu.asu.spring.quadriga.db.IDBConnectionDspaceManager;
import edu.asu.spring.quadriga.domain.factories.impl.DspaceKeysFactory;
import edu.asu.spring.quadriga.dspace.service.IDspaceKeys;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;


/**
 * This class manages the database connection to perform select,
 * insert, update and delete operations on Dspace data in Quadriga Database
 *
 * @author Ram Kumar Kumaresan
 */
public class DBConnectionDspaceManager extends ADBConnectionManager implements IDBConnectionDspaceManager {

	
	@Autowired
	private DspaceKeysFactory dsapceKeysFactory;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int addBitstreamToWorkspace(String workspaceid, String communityid, String collectionid, String itemid, String bitstreamid, String username) throws QuadrigaStorageException
	{
		String sDBCommand;
		String sOutErrorValue;

		getConnection();

		sDBCommand = DBConstants.SP_CALL + " " + DBConstants.ADD_WORKSPACE_BITSTREAM + "(?,?,?,?,?,?,?)";

		try
		{
			CallableStatement sqlStatement = connection.prepareCall("{"+sDBCommand+"}");			

			sqlStatement.setString(1, workspaceid);
			sqlStatement.setString(2, communityid);
			sqlStatement.setString(3, collectionid);
			sqlStatement.setString(4, itemid);
			sqlStatement.setString(5, bitstreamid);
			sqlStatement.setString(6, username);
			sqlStatement.registerOutParameter(7,Types.VARCHAR);

			//Execute the stored procedure
			sqlStatement.execute();

			sOutErrorValue = sqlStatement.getString(7);

			if(sOutErrorValue == null)
			{
				return SUCCESS;
			}
			else
			{
				return FAILURE;
			}
		}
		catch(SQLException e)
		{
			throw new QuadrigaStorageException(e);
		}
		finally
		{
			closeConnection();
		}		
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteBitstreamFromWorkspace(String workspaceid, String bitstreamids, String username) throws QuadrigaStorageException, QuadrigaAccessException
	{
		String sDBCommand;
		String sOutErrorValue;

		getConnection();

		sDBCommand = DBConstants.SP_CALL + " " + DBConstants.DELETE_WORKSPACE_BITSTREAM + "(?,?,?,?)";

		try
		{
			CallableStatement sqlStatement = connection.prepareCall("{"+sDBCommand+"}");			

			sqlStatement.setString(1, workspaceid);
			sqlStatement.setString(2, bitstreamids);
			sqlStatement.setString(3, username);
			sqlStatement.registerOutParameter(4,Types.VARCHAR);

			//Execute the stored procedure
			sqlStatement.execute();

			sOutErrorValue = sqlStatement.getString(4);

			if(sOutErrorValue != null)
			{
				throw new QuadrigaAccessException(sOutErrorValue);
			}
		}
		catch(SQLException e)
		{
			throw new QuadrigaStorageException(e);
		}
		finally
		{
			closeConnection();
		}		
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int addDspaceKeys(IDspaceKeys dspaceKeys, String username) throws QuadrigaStorageException
	{
		if(dspaceKeys == null)
		{
			return FAILURE;
		}
		if(dspaceKeys.getPublicKey() == null || dspaceKeys.getPrivateKey() == null || username == null)
		{
			return FAILURE;
		}
		
		if(dspaceKeys.getPublicKey().equals("") || dspaceKeys.getPrivateKey().equals("") || username.equals(""))
		{
			return FAILURE;
		}
		
		String sDBCommand;
		String sOutErrorValue;

		getConnection();

		sDBCommand = DBConstants.SP_CALL + " " + DBConstants.ADD_DSPACE_KEYS+ "(?,?,?,?)";

		try
		{
			CallableStatement sqlStatement = connection.prepareCall("{"+sDBCommand+"}");			

			sqlStatement.setString(1, dspaceKeys.getPublicKey());
			sqlStatement.setString(2, dspaceKeys.getPrivateKey());
			sqlStatement.setString(3, username);
			sqlStatement.registerOutParameter(4,Types.VARCHAR);

			//Execute the stored procedure
			sqlStatement.execute();

			sOutErrorValue = sqlStatement.getString(4);

			if(sOutErrorValue == null)
			{
				//Successfully inserted the keys into the database
				return SUCCESS;
			}			
			else
			{
				//Error occurred in the database
				return FAILURE;
			}
		}
		catch(SQLException e)
		{
			throw new QuadrigaStorageException(e);
		}
		finally
		{
			closeConnection();
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int deleteDspaceKeys(String username) throws QuadrigaStorageException
	{
		if(username == null)
		{
			return FAILURE;
		}
		
		if(username.equals(""))
		{
			return FAILURE;
		}
		
		String sDBCommand;
		String sOutErrorValue;

		getConnection();

		sDBCommand = DBConstants.SP_CALL + " " + DBConstants.DELETE_DSPACE_KEYS+ "(?,?)";

		try
		{
			CallableStatement sqlStatement = connection.prepareCall("{"+sDBCommand+"}");			

			sqlStatement.setString(1, username);
			sqlStatement.registerOutParameter(2,Types.VARCHAR);

			//Execute the stored procedure
			sqlStatement.execute();

			sOutErrorValue = sqlStatement.getString(2);

			if(sOutErrorValue == null)
			{
				//Successfully inserted the keys into the database
				return SUCCESS;
			}			
			else
			{
				//Error occurred in the database
				throw new QuadrigaStorageException(sOutErrorValue);
			}
		}
		catch(SQLException e)
		{
			throw new QuadrigaStorageException(e);
		}
		finally
		{
			closeConnection();
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public IDspaceKeys getDspaceKeys(String username) throws QuadrigaStorageException
	{
		IDspaceKeys dspaceKeys = null;		
		String sDBCommand;
		String sOutErrorValue;

		getConnection();

		sDBCommand = DBConstants.SP_CALL + " " + DBConstants.GET_DSPACE_KEYS+ "(?,?)";

		try
		{
			CallableStatement sqlStatement = connection.prepareCall("{"+sDBCommand+"}");			

			sqlStatement.setString(1, username);
			sqlStatement.registerOutParameter(2,Types.VARCHAR);

			//Execute the stored procedure
			sqlStatement.execute();

			sOutErrorValue = sqlStatement.getString(2);

			if(sOutErrorValue == null)
			{
				ResultSet result = sqlStatement.getResultSet();
				while(result.next())
				{
					dspaceKeys = dsapceKeysFactory.createDspaceKeysObject();
					dspaceKeys.setPublicKey(result.getString(1));
					dspaceKeys.setPrivateKey(result.getString(2));
				}
				return dspaceKeys;
			}			
			else
			{
				//Error occurred in the database
				throw new QuadrigaStorageException(sOutErrorValue);
			}
		}
		catch(SQLException e)
		{
			throw new QuadrigaStorageException(e);
		}
		finally
		{
			closeConnection();
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int updateDspaceKeys(IDspaceKeys dspaceKeys, String username) throws QuadrigaStorageException
	{	
		if(dspaceKeys == null)
		{
			return FAILURE;
		}
		if(dspaceKeys.getPublicKey() == null || dspaceKeys.getPrivateKey() == null || username == null)
		{
			return FAILURE;
		}
		
		if(dspaceKeys.getPublicKey().equals("") || dspaceKeys.getPrivateKey().equals("") || username.equals(""))
		{
			return FAILURE;
		}
		
		String sDBCommand;
		String sOutErrorValue;

		getConnection();

		sDBCommand = DBConstants.SP_CALL + " " + DBConstants.UPDATE_DSPACE_KEYS+ "(?,?,?,?)";

		try
		{
			CallableStatement sqlStatement = connection.prepareCall("{"+sDBCommand+"}");			

			sqlStatement.setString(1, dspaceKeys.getPublicKey());
			sqlStatement.setString(2, dspaceKeys.getPrivateKey());
			sqlStatement.setString(3, username);
			sqlStatement.registerOutParameter(4,Types.VARCHAR);

			//Execute the stored procedure
			sqlStatement.execute();

			sOutErrorValue = sqlStatement.getString(4);

			if(sOutErrorValue == null)
			{
				//Successfully inserted the keys into the database
				return SUCCESS;
			}			
			else
			{
				//Thrown when no keys were found for the user
				throw new QuadrigaStorageException(sOutErrorValue);
			}
		}
		catch(SQLException e)
		{
			throw new QuadrigaStorageException(e);
		}
		finally
		{
			closeConnection();
		}
	}

	@Override
	public int saveOrUpdateDspaceKeys(IDspaceKeys dspaceKeys, String username)
			throws QuadrigaStorageException {
		// TODO Auto-generated method stub
		return 0;
	}
	
}