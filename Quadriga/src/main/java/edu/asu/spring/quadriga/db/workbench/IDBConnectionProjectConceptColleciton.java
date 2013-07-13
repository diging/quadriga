package edu.asu.spring.quadriga.db.workbench;

import java.sql.SQLException;
import java.util.List;

import edu.asu.spring.quadriga.domain.IConceptCollection;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IDBConnectionProjectConceptColleciton {

	/**
	 *  Method add a dictionary to a project                   
	 * 
	 * @returns         path of list project collection page
	 * 
	 * @throws			SQLException
	 *                     
	 * @author          Lohith Dwaraka
	 * 
	 */
	public abstract String addProjectConceptCollection(String projectId,
			String conceptCollectionId, String userId) throws QuadrigaStorageException;

	public abstract List<IConceptCollection> listProjectConceptCollection(String projectId, String userId)
			throws QuadrigaStorageException;

	public abstract  String deleteProjectConceptCollection(String projectId, String userId,
			String conceptCollectionId) throws QuadrigaStorageException;

}