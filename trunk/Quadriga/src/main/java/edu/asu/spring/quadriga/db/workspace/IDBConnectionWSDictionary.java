package edu.asu.spring.quadriga.db.workspace;

import java.sql.SQLException;

import javax.sql.DataSource;

import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IDBConnectionWSDictionary {

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
	public abstract String addWSDictionary(String projectId,
			String dictionaryId, String userId) throws QuadrigaStorageException;

}