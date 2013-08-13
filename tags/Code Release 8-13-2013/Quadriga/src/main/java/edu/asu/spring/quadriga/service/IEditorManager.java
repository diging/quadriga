package edu.asu.spring.quadriga.service;

import java.util.List;

import edu.asu.spring.quadriga.domain.INetwork;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IEditorManager {

	public abstract List<INetwork> getEditorNetworkList(IUser user)
			throws QuadrigaStorageException;

}