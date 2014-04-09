package edu.asu.spring.quadriga.service.impl.workbench;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.spring.quadriga.db.workbench.IDBConnectionProjectDictionary;
import edu.asu.spring.quadriga.domain.IDictionary;
import edu.asu.spring.quadriga.domain.IProject;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.workbench.IProjectDictionaryManager;

/**
 * This class acts a service layer to associate {@link IProject} and {@link IDictionary}.
 * 
 * @author Lohith Dwaraka
 *
 */
@Service
public class ProjectDictionaryManager implements IProjectDictionaryManager {

	@Autowired
	private IDBConnectionProjectDictionary dbConnect;
	
	/**
	 * This class helps in adding dictionary to the project 
	 * @param projectId								{@link IProject} ID of type {@link String} 
	 * @param dictionaryId						  	{@link IDictionary} ID of type {@link String}
	 * @param userId								{@link IUser} ID of type {@link String}
	 * @throws QuadrigaStorageException				Throws Storage exception when there is a issue with access to DB
	 */
	@Override
	@Transactional
	public void addDictionaryToProject(String projectId, String dictionaryId,
			String userId) throws QuadrigaStorageException {
		dbConnect.addProjectDictionary(projectId, dictionaryId, userId);
	}

	/**
	 * This class helps in getting {@link List} the {@link IDictionary} from {@link IProject} and {@link IUser} Id
	 * @param projectId								{@link IProject} ID of type {@link String} 
	 * @param userId								{@link IUser} ID of type {@link String}							
	 * @return										Returns the of {@link List} of {@link IDictionary} 
	 * @throws QuadrigaStorageException				Throws Storage exception when there is a issue with access to DB
	 */
	@Override
	@Transactional
	public List<IDictionary> listProjectDictionary(String projectId,
			String userId) throws QuadrigaStorageException {
		List<IDictionary> dictionaryList = dbConnect.listProjectDictionary(projectId, userId);
		return dictionaryList;
	}

	/**
	 * This class helps in deleting the {@link IDictionary} of a {@link IProject} using {@link IProject} ID, {@link IDictionary} ID and {@link IUser} Id
	 * @param projectId								{@link IProject} ID of type {@link String} 
	 * @param userId								{@link IUser} ID of type {@link String}		
	 * @param dictioanaryId						  	{@link IDictionary} ID of type {@link String}
	 * @throws QuadrigaStorageException				Throws Storage exception when there is a issue with access to DB
	 */
	@Override
	@Transactional
	public void deleteProjectDictionary(String projectId,String userId,String dictioanaryId)throws QuadrigaStorageException{
		dbConnect.deleteProjectDictionary(projectId, userId, dictioanaryId);
	}
}
