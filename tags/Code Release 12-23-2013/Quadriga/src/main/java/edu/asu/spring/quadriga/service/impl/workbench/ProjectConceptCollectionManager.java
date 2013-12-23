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
	
	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.service.impl.workbench.IProjectConceptCollectionManager#addProjectDictionary(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional
	public void addProjectConceptCollection(String projectId, String conceptCollectionId,
			String userId) throws QuadrigaStorageException 
	{
		dbConnect.addProjectConceptCollection(projectId, conceptCollectionId, userId);
	}
	
	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.service.impl.workbench.IProjectConceptCollectionManager#listProjectDictionary(java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional
	public List<IConceptCollection> listProjectConceptCollection(String projectId,
			String userId) throws QuadrigaStorageException {
		List<IConceptCollection> conceptCollectionList  = dbConnect.listProjectConceptCollection(projectId, userId);
		return conceptCollectionList;
	}
	
	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.service.impl.workbench.IProjectConceptCollectionManager#deleteProjectDictionary(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional
	public void deleteProjectConceptCollection(String projectId,String userId,String conceptCollectionId)throws QuadrigaStorageException
	{
		dbConnect.deleteProjectConceptCollection(projectId, userId, conceptCollectionId);
	}
}
