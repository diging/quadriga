package edu.asu.spring.quadriga.db.workspace;

import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import edu.asu.spring.quadriga.domain.IDictionary;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IDBConnectionProjectDictionary {

	/**
	 *  Assigns the data source
	 *  @param  dataSource
	 *  @author Lohith Dwaraka
	 */
	//@Override
	public abstract void setDataSource(DataSource dataSource);

	/**
	 *  Method add a dictionary to a project                   
	 * 
	 * @returns         path of list project dicitonary page
	 * 
	 * @throws			SQLException
	 *                     
	 * @author          Lohith Dwaraka
	 * 
	 */
	public abstract String addProjectDictionary(String projectId,
			String dictionaryId, String userId) throws QuadrigaStorageException;

	public abstract List<IDictionary> listProjectDictionary(String projectId, String userId)
			throws QuadrigaStorageException;

	public abstract  String deleteProjectDictionary(String projectId, String userId,
			String dictioanaryId) throws QuadrigaStorageException;

}