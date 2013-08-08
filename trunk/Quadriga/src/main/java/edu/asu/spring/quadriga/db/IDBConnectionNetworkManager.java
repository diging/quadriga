package edu.asu.spring.quadriga.db;

import edu.asu.spring.quadriga.domain.INetwork;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IDBConnectionNetworkManager {


	public String addNetworkRequest(String networkName, IUser user)
			throws QuadrigaStorageException;

	public String addNetworkStatement(String networkId, String id, String type,
			String isTop, IUser user) throws QuadrigaStorageException;
}