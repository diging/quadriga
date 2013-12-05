package edu.asu.spring.quadriga.service.impl.workbench;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.spring.quadriga.dao.workbench.IProjectConceptCollectionDAO;
import edu.asu.spring.quadriga.domain.IConceptCollection;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.workbench.IProjectConceptCollectionManager;

@Service
public class ProjectConceptCollectionManager implements IProjectConceptCollectionManager {

	@Autowired
	private IProjectConceptCollectionDAO projectConceptCollectionDAO;
	
	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.service.impl.workbench.IProjectConceptCollectionManager#addProjectDictionary(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional
	public void addProjectConceptCollection(String projectId, String conceptCollectionId,
			String userId) throws QuadrigaStorageException 
	{
		projectConceptCollectionDAO.addProjectConceptCollection(projectId, conceptCollectionId, userId);
	}
	
	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.service.impl.workbench.IProjectConceptCollectionManager#listProjectDictionary(java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional
	public List<IConceptCollection> listProjectConceptCollection(String projectId,
			String userId) throws QuadrigaStorageException {
		List<IConceptCollection> conceptCollectionList  = projectConceptCollectionDAO.listProjectConceptCollection(projectId, userId);
		return conceptCollectionList;
	}
	
	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.service.impl.workbench.IProjectConceptCollectionManager#deleteProjectDictionary(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional
	public void deleteProjectConceptCollection(String projectId,String userId,String conceptCollectionId)throws QuadrigaStorageException
	{
		projectConceptCollectionDAO.deleteProjectConceptCollection(projectId, userId, conceptCollectionId);
	}
}
