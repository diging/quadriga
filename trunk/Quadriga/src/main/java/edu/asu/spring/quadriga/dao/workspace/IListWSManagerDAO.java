package edu.asu.spring.quadriga.dao.workspace;

import java.util.List;

import edu.asu.spring.quadriga.domain.IWorkSpace;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IListWSManagerDAO {

	List<IWorkSpace> listDeactivatedWorkspace(String projectid, String user)
			throws QuadrigaStorageException;

	List<IWorkSpace> listActiveWorkspace(String projectid, String username)
			throws QuadrigaStorageException;

}
