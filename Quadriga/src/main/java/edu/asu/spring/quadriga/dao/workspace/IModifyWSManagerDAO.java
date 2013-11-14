package edu.asu.spring.quadriga.dao.workspace;

import edu.asu.spring.quadriga.domain.IWorkSpace;
import edu.asu.spring.quadriga.dto.QuadrigaUserDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IModifyWSManagerDAO {

	void addWorkSpaceRequest(IWorkSpace workSpace, String projectId)
			throws QuadrigaStorageException;

	public abstract QuadrigaUserDTO getUserDTO(String userName)
			throws QuadrigaStorageException;

	public abstract void transferWSOwnerRequest(String workspaceId, String oldOwner,
			String newOwner, String collabRole) throws QuadrigaStorageException;

}