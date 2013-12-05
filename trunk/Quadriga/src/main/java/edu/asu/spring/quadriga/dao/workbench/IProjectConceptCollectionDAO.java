package edu.asu.spring.quadriga.dao.workbench;

import java.util.List;

import edu.asu.spring.quadriga.domain.IConceptCollection;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IProjectConceptCollectionDAO
{

	public abstract void deleteProjectConceptCollection(String projectId,
			String userId, String conceptCollectionId) throws QuadrigaStorageException;

	public abstract List<IConceptCollection> listProjectConceptCollection(String projectId,
			String userId) throws QuadrigaStorageException;

	public abstract void addProjectConceptCollection(String projectId,
			String conceptCollectionId, String userId) throws QuadrigaStorageException;

}
