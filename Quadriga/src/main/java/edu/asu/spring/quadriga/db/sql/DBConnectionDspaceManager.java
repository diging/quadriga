package edu.asu.spring.quadriga.db.sql;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;

import edu.asu.spring.quadriga.db.IDBConnectionDspaceManager;
import edu.asu.spring.quadriga.domain.IBitStream;
import edu.asu.spring.quadriga.domain.factories.impl.DspaceKeysFactory;
import edu.asu.spring.quadriga.domain.implementation.BitStream;
import edu.asu.spring.quadriga.dspace.service.IDspaceKeys;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;


/**
 * This class manages the database connection to perform select,
 * insert, update and delete operations on Dspace data in Quadriga Database
 *
 * @author Ram Kumar Kumaresan
 */
public class DBConnectionDspaceManager implements IDBConnectionDspaceManager {

	private Connection connection;

	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private DspaceKeysFactory dsapceKeysFactory;
	
	@Override
	public DataSource getDataSource() {
		return dataSource;
	}

	/**
	 * @Description: Assigns the data source used by this class
	 * 
	 * @param : dataSource
	 */
	@Override
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
	 * 
	 */
	private void getConnection() throws QuadrigaStorageException {
		try {
			connection = dataSource.getConnection();
		} catch (SQLException e) {
			throw new QuadrigaStorageException(e);
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
			throw new QuadrigaStorageException(e);
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
			throw new QuadrigaStorageException(e);
		}
		finally
		{
			closeConnection();
		}		
	}
	
	@Override
	public int addBitstreamToWorkspace(String workspaceid, String bitstreamid, String username) throws QuadrigaStorageException, QuadrigaAccessException
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
	
	
	@Override
	public int updateCommunity(String communityid, String name, String shortDescription, String introductoryText, String handle, String username) throws QuadrigaStorageException
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

		sDBCommand = DBConstants.SP_CALL + " " + DBConstants.UPDATE_DSPACE_COMMUNITY+ "(?,?,?,?,?,?,?)";

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
			throw new QuadrigaStorageException(e);
		}
		finally
		{
			closeConnection();
		}
	}
	
	@Override
	public int updateCollection(String communityid, String collectionid, String name, String shortDescription, String entityReference, String handle, String username) throws QuadrigaStorageException
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

		sDBCommand = DBConstants.SP_CALL + " " + DBConstants.UPDATE_DSPACE_COLLECTION+ "(?,?,?,?,?,?,?,?)";

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
			throw new QuadrigaStorageException(e);
		}
		finally
		{
			closeConnection();
		}
	}
	
	@Override
	public int updateItem(String communityid, String collectionid, String itemid, String name, String handle, String username) throws QuadrigaStorageException
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

		sDBCommand = DBConstants.SP_CALL + " " + DBConstants.UPDATE_DSPACE_ITEM+ "(?,?,?,?,?,?,?)";

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
			throw new QuadrigaStorageException(e);
		}
		finally
		{
			closeConnection();
		}
	}
	
	@Override
	public int updateBitStream(String communityid, String collectionid, String itemid, String bitstreamid, String name, String size, String mimeType, String username) throws QuadrigaStorageException
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

		sDBCommand = DBConstants.SP_CALL + " " + DBConstants.UPDATE_DSPACE_BITSTREAM+ "(?,?,?,?,?,?,?,?,?)";

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
			throw new QuadrigaStorageException(e);
		}
		finally
		{
			closeConnection();
		}
	}
	
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
	public List<IBitStream> getBitStreamReferences(String workspaceId, String username) throws QuadrigaAccessException, QuadrigaStorageException
	{
		String sDBCommand;
		String sOutErrorValue;

		getConnection();

		sDBCommand = DBConstants.SP_CALL + " " + DBConstants.GET_DSPACE_REFERENCES + "(?,?,?)";

		try
		{
			CallableStatement sqlStatement = connection.prepareCall("{"+sDBCommand+"}");			

			sqlStatement.setString(1, workspaceId);
			sqlStatement.setString(2, username);
			sqlStatement.registerOutParameter(3,Types.VARCHAR);

			//Execute the stored procedure
			sqlStatement.execute();

			sOutErrorValue = sqlStatement.getString(3);

			if(sOutErrorValue == null)
			{
				//Successfully retrieved the bitstreams associated with the workspace
				List<IBitStream> bitstreamList = new ArrayList<IBitStream>();
				ResultSet result = sqlStatement.getResultSet();
				
				IBitStream bitstream = null;
				while(result.next())
				{
					bitstream = new BitStream();
					bitstream.setCommunityid(result.getString(1));
					bitstream.setCollectionid(result.getString(2));
					bitstream.setItemid(result.getString(3));
					bitstream.setId(result.getString(4));
					bitstreamList.add(bitstream);
				}
				
				return bitstreamList;
			}
			else
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
}
