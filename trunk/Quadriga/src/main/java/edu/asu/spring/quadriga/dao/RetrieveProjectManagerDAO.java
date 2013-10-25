package edu.asu.spring.quadriga.dao;

import edu.asu.spring.quadriga.domain.IProject;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface RetrieveProjectManagerDAO {

	public abstract IProject getProjectDetails(String projectId)
			throws QuadrigaStorageException;

}