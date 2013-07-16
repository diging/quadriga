package edu.asu.spring.quadriga.db.sql;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import edu.asu.spring.quadriga.db.IDBConnectionDspaceManager;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

/**
 * @Description This class is manages the database connection to perform select,
 *              insert operations on Dspace data in Quadriga Database
 * 
 * @implements IDBConnectionDspaceManager interface.
 * 
 * @Called By DspaceManager.java
 * 
 * @author Ram Kumar Kumaresan
 * 
 */
public class DBConnectionDspaceManager implements IDBConnectionDspaceManager {

	private Connection connection;

	@Autowired
	private DataSource dataSource;
	
	private static final Logger logger = LoggerFactory
			.getLogger(IDBConnectionDspaceManager.class);

	/**
	 * @Description: Assigns the data source used by this class
	 * 
	 * @param : dataSource
	 */
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	/**
	 * Close the database connection
	 * 
	 * @throws QuadrigaStorageException
	 * @author Ram Kumar Kumaresan
	 */
	private void closeConnection() throws QuadrigaStorageException {
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException e) {
			throw new QuadrigaStorageException();
		}
	}

	/**
	 * Establishes connection with the Quadriga database
	 * 
	 * @return The connection handle which is used to query the database
	 * @throws QuadrigaStorageException
	 * @author Ram Kumar Kumaresan
	 */
	private void getConnection() throws QuadrigaStorageException {
		try {
			connection = dataSource.getConnection();
		} catch (SQLException e) {
			throw new QuadrigaStorageException();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int addCommunity(String communityid, String name, String shortDescription, String introductoryText, String handle, String username) throws QuadrigaStorageException
	{
		if(communityid == null || handle == null || username == null)
		{
			return FAILURE;
		}
		
		if(communityid.equals("") || handle.equals("") || username.equals(""))
		{
			return FAILURE;
		}
		
		String sDBCommand;
		String sOutErrorValue;

		getConnection();

		sDBCommand = DBConstants.SP_CALL + " " + DBConstants.ADD_DSPACE_COMMUNITY+ "(?,?,?,?,?,?,?)";

		try
		{
			CallableStatement sqlStatement = connection.prepareCall("{"+sDBCommand+"}");			

			sqlStatement.setString(1, communityid);
			sqlStatement.setString(2, name);
			sqlStatement.setString(3, shortDescription);
			sqlStatement.setString(4, introductoryText);
			sqlStatement.setString(5, handle);
			sqlStatement.setString(6, username);
			sqlStatement.registerOutParameter(7,Types.VARCHAR);

			//Execute the stored procedure
			sqlStatement.execute();

			sOutErrorValue = sqlStatement.getString(7);

			if(sOutErrorValue == null)
			{
				//Successfully inserted the community details into the database
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
			throw new QuadrigaStorageException();
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
	public int addCollection(String communityid, String collectionid, String name, String shortDescription, String entityReference, String handle, String username) throws QuadrigaStorageException
	{
		if(communityid == null || collectionid == null || handle == null || username == null)
		{
			return FAILURE;
		}
		
		if(communityid.equals("") || collectionid.equals("") || handle.equals("") || username.equals(""))
		{
			return FAILURE;
		}
		
		
		String sDBCommand;
		String sOutErrorValue;

		getConnection();

		sDBCommand = DBConstants.SP_CALL + " " + DBConstants.ADD_DSPACE_COLLECTION+ "(?,?,?,?,?,?,?,?)";

		try
		{
			CallableStatement sqlStatement = connection.prepareCall("{"+sDBCommand+"}");			

			sqlStatement.setString(1, communityid);
			sqlStatement.setString(2, collectionid);
			sqlStatement.setString(3, name);
			sqlStatement.setString(4, shortDescription);
			sqlStatement.setString(5, entityReference);
			sqlStatement.setString(6, handle);
			sqlStatement.setString(7, username);
			sqlStatement.registerOutParameter(8,Types.VARCHAR);

			//Execute the stored procedure
			sqlStatement.execute();

			sOutErrorValue = sqlStatement.getString(8);

			if(sOutErrorValue == null)
			{
				//Successfully inserted the collection details into the database
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
			throw new QuadrigaStorageException();
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
	public int addItem(String communityid, String collectionid, String itemid, String name, String handle, String username) throws QuadrigaStorageException
	{
		if(communityid == null || collectionid == null || itemid == null || handle == null || username == null)
		{
			return FAILURE;
		}
		
		if(communityid.equals("") || collectionid.equals("") || itemid.equals("") || handle.equals("") || username.equals(""))
		{
			return FAILURE;
		}
		
		
		String sDBCommand;
		String sOutErrorValue;

		getConnection();

		sDBCommand = DBConstants.SP_CALL + " " + DBConstants.ADD_DSPACE_ITEM+ "(?,?,?,?,?,?,?)";

		try
		{
			CallableStatement sqlStatement = connection.prepareCall("{"+sDBCommand+"}");			

			sqlStatement.setString(1, communityid);
			sqlStatement.setString(2, collectionid);
			sqlStatement.setString(3, itemid);
			sqlStatement.setString(4, name);
			sqlStatement.setString(5, handle);
			sqlStatement.setString(6, username);
			sqlStatement.registerOutParameter(7,Types.VARCHAR);

			//Execute the stored procedure
			sqlStatement.execute();

			sOutErrorValue = sqlStatement.getString(7);

			if(sOutErrorValue == null)
			{
				//Successfully inserted the item details into the database
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
			throw new QuadrigaStorageException();
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
	public int addBitStream(String communityid, String collectionid, String itemid, String bitstreamid, String name, String size, String mimeType, String username) throws QuadrigaStorageException
	{
		if(communityid == null || collectionid == null || itemid == null || bitstreamid == null || name == null || username == null)
		{
			return FAILURE;
		}
		
		if(communityid.equals("") || collectionid.equals("") || itemid.equals("") || bitstreamid.equals("") || name.equals("") || username.equals(""))
		{
			return FAILURE;
		}
		
		String sDBCommand;
		String sOutErrorValue;

		getConnection();

		sDBCommand = DBConstants.SP_CALL + " " + DBConstants.ADD_DSPACE_BITSTREAM+ "(?,?,?,?,?,?,?,?,?)";

		try
		{
			CallableStatement sqlStatement = connection.prepareCall("{"+sDBCommand+"}");			

			sqlStatement.setString(1, communityid);
			sqlStatement.setString(2, collectionid);
			sqlStatement.setString(3, itemid);
			sqlStatement.setString(4, bitstreamid);
			sqlStatement.setString(5, name);
			sqlStatement.setString(6, size);
			sqlStatement.setString(7, mimeType);
			sqlStatement.setString(8, username);
			sqlStatement.registerOutParameter(9,Types.VARCHAR);

			//Execute the stored procedure
			sqlStatement.execute();

			sOutErrorValue = sqlStatement.getString(9);

			if(sOutErrorValue == null)
			{
				//Successfully inserted the item details into the database
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
			throw new QuadrigaStorageException();
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
	public String checkDspaceNodes(String communityid, String collectionid, String itemid) throws QuadrigaStorageException
	{		
		String sDBCommand;
		String sDspaceDataStatus;

		getConnection();

		sDBCommand = DBConstants.SP_CALL + " " + DBConstants.CHECK_DSPACEDATA_NODES + "(?,?,?,?)";

		try
		{
			CallableStatement sqlStatement = connection.prepareCall("{"+sDBCommand+"}");			

			sqlStatement.setString(1, communityid);
			sqlStatement.setString(2, collectionid);
			sqlStatement.setString(3, itemid);
			sqlStatement.registerOutParameter(4,Types.VARCHAR);

			//Execute the stored procedure
			sqlStatement.execute();

			sDspaceDataStatus = sqlStatement.getString(4);

			return sDspaceDataStatus;
		}
		catch(SQLException e)
		{
			throw new QuadrigaStorageException();
		}
		finally
		{
			closeConnection();
		}		
	}
	
	@Override
	public String checkDspaceBitStream(String bitstreamid) throws QuadrigaStorageException
	{		
		String sDBCommand;
		String sDspaceDataStatus;

		getConnection();

		sDBCommand = DBConstants.SP_CALL + " " + DBConstants.CHECK_DSPACEDATA_BITSTREAM + "(?,?)";

		try
		{
			CallableStatement sqlStatement = connection.prepareCall("{"+sDBCommand+"}");			

			sqlStatement.setString(1, bitstreamid);
			sqlStatement.registerOutParameter(2,Types.VARCHAR);

			//Execute the stored procedure
			sqlStatement.execute();

			sDspaceDataStatus = sqlStatement.getString(2);

			return sDspaceDataStatus;
		}
		catch(SQLException e)
		{
			throw new QuadrigaStorageException();
		}
		finally
		{
			closeConnection();
		}		
	}
	
	@Override
	public int addBitstreamToWorkspace(String workspaceid, String bitstreamid, String username) throws QuadrigaStorageException
	{
		String sDBCommand;
		String sOutErrorValue;

		getConnection();

		sDBCommand = DBConstants.SP_CALL + " " + DBConstants.ADD_WORKSPACE_BITSTREAM + "(?,?,?,?)";

		try
		{
			CallableStatement sqlStatement = connection.prepareCall("{"+sDBCommand+"}");			

			sqlStatement.setString(1, workspaceid);
			sqlStatement.setString(2, bitstreamid);
			sqlStatement.setString(3, username);
			sqlStatement.registerOutParameter(4,Types.VARCHAR);

			//Execute the stored procedure
			sqlStatement.execute();

			sOutErrorValue = sqlStatement.getString(4);

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
			throw new QuadrigaStorageException();
		}
		finally
		{
			closeConnection();
		}		
	}
	
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
			throw new QuadrigaStorageException(e.getMessage());
		}
		finally
		{
			closeConnection();
		}		
	}
}
