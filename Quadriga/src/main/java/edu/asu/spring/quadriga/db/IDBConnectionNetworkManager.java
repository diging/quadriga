package edu.asu.spring.quadriga.db;

import java.util.List;

import edu.asu.spring.quadriga.domain.INetwork;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IDBConnectionNetworkManager {


	public abstract String addNetworkRequest(String networkName, IUser user)
			throws QuadrigaStorageException;

	public abstract String addNetworkStatement(String networkId, String id, String type,
			String isTop, IUser user) throws QuadrigaStorageException;

	public abstract INetwork getNetworkStatus(String networkName, IUser user)
			throws QuadrigaStorageException;

	public abstract List<INetwork> getNetworkList(IUser user) throws QuadrigaStorageException;
}