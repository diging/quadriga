package edu.asu.spring.quadriga.dao.workbench;

import java.util.List;

import edu.asu.spring.quadriga.domain.IDictionary;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IProjectDictionaryMangerDAO 
{

	public abstract void deleteProjectDictionary(String projectId, String userId,
			String dictionaryId);

	public abstract List<IDictionary> listProjectDictionary(String projectId, String userId)
			throws QuadrigaStorageException;

	public abstract void addProjectDictionary(String projectId, String dictionaryId,
			String userId) throws QuadrigaStorageException;

}
