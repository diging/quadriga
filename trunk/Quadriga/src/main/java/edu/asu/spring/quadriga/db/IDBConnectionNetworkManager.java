package edu.asu.spring.quadriga.db;

import edu.asu.spring.quadriga.domain.INetwork;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IDBConnectionNetworkManager {

	public abstract String submitUserNetworkRequest(INetwork network) throws QuadrigaStorageException;
}