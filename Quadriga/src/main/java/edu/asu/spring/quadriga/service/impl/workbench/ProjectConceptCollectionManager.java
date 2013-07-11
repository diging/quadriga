package edu.asu.spring.quadriga.service.impl.workbench;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.db.workspace.IDBConnectionProjectConceptColleciton;
import edu.asu.spring.quadriga.domain.IConceptCollection;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.workbench.IProjectConceptCollectionManager;

@Service
public class ProjectConceptCollectionManager implements IProjectConceptCollectionManager {

	@Autowired
	@Qualifier("DBConnectionProjectConceptCollecitonBean")
	private IDBConnectionProjectConceptColleciton dbConnect;
	
	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.service.impl.workbench.IProjectConceptCollectionManager#addProjectDictionary(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public String addProjectConceptCollection(String projectId, String conceptCollectionId,
			String userId) throws QuadrigaStorageException {
		String msg = dbConnect.addProjectConceptCollection(projectId, conceptCollectionId, userId);
		return msg;
	}
	
	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.service.impl.workbench.IProjectConceptCollectionManager#listProjectDictionary(java.lang.String, java.lang.String)
	 */
	@Override
	public List<IConceptCollection> listProjectConceptCollection(String projectId,
			String userId) throws QuadrigaStorageException {
		List<IConceptCollection> conceptCollectionList = dbConnect.listProjectConceptCollection(projectId, userId);
		return conceptCollectionList;
	}
	
	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.service.impl.workbench.IProjectConceptCollectionManager#deleteProjectDictionary(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public String deleteProjectConceptCollection(String projectId,String userId,String conceptCollectionId)throws QuadrigaStorageException{
		
		String msg=dbConnect.deleteProjectConceptCollection(projectId, userId, conceptCollectionId);
		return msg;
	}
}
