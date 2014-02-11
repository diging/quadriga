package edu.asu.spring.quadriga.service.impl.workbench;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.spring.quadriga.db.workbench.IDBConnectionProjectConceptColleciton;
import edu.asu.spring.quadriga.domain.IConceptCollection;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.workbench.IProjectConceptCollectionManager;

@Service
public class ProjectConceptCollectionManager implements IProjectConceptCollectionManager {

	@Autowired
	private IDBConnectionProjectConceptColleciton dbConnect;
	

	/**
	 * This method associates the concept collection with the project.
	 * @param - projectId - project id
	 * @param - conceptCollectionId - concept collection id
	 * @param - userId - logged in user name.
	 * @throws QuarigaStorageException
	 */
	@Override
	@Transactional
	public void addProjectConceptCollection(String projectId, String conceptCollectionId,
			String userId) throws QuadrigaStorageException 
	{
		dbConnect.addProjectConceptCollection(projectId, conceptCollectionId, userId);
	}
	

    /**
     * This method retrieves the concept collection associated with the project.
     * @param - projectId project id
     * @param - userId - logged in user name.
     * @throws QuadrigaStorageException
     * @return List<IConceptCollection> - list of concept collection associated with the project.
     */
	@Override
	@Transactional
	public List<IConceptCollection> listProjectConceptCollection(String projectId,
			String userId) throws QuadrigaStorageException {
		List<IConceptCollection> conceptCollectionList  = dbConnect.listProjectConceptCollection(projectId, userId);
		return conceptCollectionList;
	}
	

	/**
	 * This method removes the association between the project and the concept collection.
	 * @param projectId - project id
	 * @param userId - logged in user name.
	 * @param conceptCollectionId - concept collection id.
	 * @throws QuadrigaStorageException
	 */
	@Override
	@Transactional
	public void deleteProjectConceptCollection(String projectId,String userId,String conceptCollectionId)throws QuadrigaStorageException
	{
		dbConnect.deleteProjectConceptCollection(projectId, userId, conceptCollectionId);
	}
}
