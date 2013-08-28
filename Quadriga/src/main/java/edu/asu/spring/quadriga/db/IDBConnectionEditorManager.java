package edu.asu.spring.quadriga.db;

import java.util.List;

import edu.asu.spring.quadriga.domain.INetwork;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IDBConnectionEditorManager {

	public abstract List<INetwork> getEditorNetworkList(IUser user)
			throws QuadrigaStorageException;

	public abstract String assignNetworkToUser(String networkId, IUser user)
			throws QuadrigaStorageException;

	public abstract List<INetwork> getAssignNetworkOfUser(IUser user)
			throws QuadrigaStorageException;


}