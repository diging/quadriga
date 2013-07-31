package edu.asu.spring.quadriga.db;

import java.util.List;

import javax.sql.DataSource;

import edu.asu.spring.quadriga.domain.IBitStream;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IDBConnectionDspaceManager {

	public final static int SUCCESS = 1;
	public final static int FAILURE = 0;

	public abstract int addCommunity(String communityid, String name,
			String shortDescription, String introductoryText, String handle,
			String username) throws QuadrigaStorageException;

	public abstract int addCollection(String communityid, String collectionid,
			String name, String shortDescription, String entityReference,
			String handle, String username) throws QuadrigaStorageException;

	public abstract int addItem(String communityid, String collectionid,
			String itemid, String name, String handle, String username) throws QuadrigaStorageException;

	public abstract int addBitStream(String communityid, String collectionid,
			String itemid, String bitstreamid, String name, String size,
			String mimeType, String username) throws QuadrigaStorageException;

	public abstract String checkDspaceNodes(String communityid, String collectionid,
			String itemid) throws QuadrigaStorageException;

	public abstract String checkDspaceBitStream(String bitstreamid)
			throws QuadrigaStorageException;
	
	public abstract int addBitstreamToWorkspace(String workspaceid, String bitstreamid,
			String username) throws QuadrigaStorageException;

	public abstract void deleteBitstreamFromWorkspace(String workspaceid, String bitstreamids,
			String username) throws QuadrigaStorageException, QuadrigaAccessException;

	public abstract List<IBitStream> getBitStreamReferences(String workspaceId, String username)
			throws QuadrigaAccessException, QuadrigaStorageException;

	public abstract int updateBitStream(String communityid, String collectionid,
			String itemid, String bitstreamid, String name, String size, String mimeType,
			String username) throws QuadrigaStorageException;

	public abstract int updateItem(String communityid, String collectionid, String itemid,
			String name, String handle, String username)
			throws QuadrigaStorageException;

	public abstract int updateCollection(String communityid, String collectionid,
			String name, String shortDescription, String entityReference, String handle, String username)
			throws QuadrigaStorageException;

	public abstract int updateCommunity(String communityid, String name,
			String shortDescription, String introductoryText, String handle, String username)
			throws QuadrigaStorageException;

	public abstract void setDataSource(DataSource dataSource);

	public abstract DataSource getDataSource();

}