package edu.asu.spring.quadriga.dao;

import edu.asu.spring.quadriga.domain.IProject;
import edu.asu.spring.quadriga.dto.QuadrigaUserDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface ModifyProjectManagerDAO {

	public abstract void transferProjectOwnerRequest(String projectId,
			String oldOwner, String newOwner, String collabRole)
			throws QuadrigaStorageException;

	public abstract QuadrigaUserDTO getProjectOwner(String userName)
			throws QuadrigaStorageException;

	void addProjectRequest(IProject project) throws QuadrigaStorageException;

	void deleteProjectRequest(String projectIdList)
			throws QuadrigaStorageException;

}